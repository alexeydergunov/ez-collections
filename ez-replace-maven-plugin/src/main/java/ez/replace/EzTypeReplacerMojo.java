package ez.replace;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("UnusedDeclaration")
@Mojo(name = "replace-types")
public class EzTypeReplacerMojo extends AbstractMojo {
    private static enum TypeInfo {
        BOOLEAN("boolean", "Boolean", "Boolean"),
        BYTE("byte", "Byte", "Byte"),
        SHORT("short", "Short", "Short"),
        CHAR("char", "Char", "Character"),
        INT("int", "Int", "Integer"),
        LONG("long", "Long", "Long"),
        FLOAT("float", "Float", "Float"),
        DOUBLE("double", "Double", "Double");

        private final String primitiveName;
        private final String typeName;
        private final String wrapperName;

        private TypeInfo(String primitiveName, String typeName, String wrapperName) {
            this.primitiveName = primitiveName;
            this.typeName = typeName;
            this.wrapperName = wrapperName;
        }
    }

    private static class UnsupportedTypeException extends Exception {
        private UnsupportedTypeException(String message) {
            super(message);
        }
    }

    private static final String TOKEN_DELIMITERS = "[](){}<>.,;";

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("_.*?_");
    private static final Pattern PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*T\\*/.*/\\*T\\*/");
    private static final Pattern COMPARABLE_PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*C\\*/.*/\\*C\\*/");
    private static final Pattern KEY_PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*K\\*/.*/\\*K\\*/");
    private static final Pattern COMPARABLE_KEY_PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*KC\\*/.*/\\*KC\\*/");
    private static final Pattern VALUE_PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*V\\*/.*/\\*V\\*/");
    private static final Pattern WRAPPER_TYPE_PATTERN = Pattern.compile("/\\*W\\*/.*/\\*W\\*/");
    private static final Pattern COMPARABLE_WRAPPER_TYPE_PATTERN = Pattern.compile("/\\*WC\\*/.*/\\*WC\\*/");

    private static final FileFilter JAVA_FILTER = new FileFilter() {
        @Override
        public boolean accept(@SuppressWarnings("NullableProblems") File file) {
            return file.isDirectory() || file.getName().endsWith(".java");
        }
    };

    @SuppressWarnings("UnusedDeclaration")
    @Parameter
    private File sourceDirectory;

    @SuppressWarnings("UnusedDeclaration")
    @Parameter
    private File targetDirectory;

    public void execute() throws MojoExecutionException {
        getLog().info("sourceDirectory = " + sourceDirectory);
        getLog().info("targetDirectory = " + targetDirectory);
        try {
            //noinspection ResultOfMethodCallIgnored
            targetDirectory.mkdirs();
            processTree(sourceDirectory, targetDirectory);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private void processTree(File source, File target) throws IOException {
        if (source.isFile()) {
            throw new IllegalArgumentException("Source is file");
        }
        File[] children = source.listFiles(JAVA_FILTER);
        for (File childSource : children) {
            File childTarget = new File(target, childSource.getName());
            if (childSource.isFile()) {
                processFile(childSource, childTarget);
            } else {
                if (!childTarget.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    childTarget.mkdir();
                }
                processTree(childSource, childTarget);
            }
        }
    }

    private void processFile(File source, File target) throws IOException {
        String fileName = source.getName();
        String className = fileName.substring(0, fileName.length() - 5);
        if (className.startsWith("_")) {
            className = className.substring(1);
            int replacementsCount = 0;
            Matcher matcher = CLASS_NAME_PATTERN.matcher(className);
            while (matcher.find()) {
                replacementsCount++;
            }
            if (replacementsCount == 1) {
                // collection
                for (TypeInfo typeInfo : TypeInfo.values()) {
                    String generatedClassName = replaceSequentially(
                            CLASS_NAME_PATTERN, className, typeInfo.typeName);
                    File generatedTarget = new File(target.getParent(), generatedClassName + ".java");
                    if (!generatedTarget.exists()) {
                        generateSourceCode(source, generatedTarget, typeInfo);
                    }
                }
                return;
            }
            if (replacementsCount == 2) {
                // map
                for (TypeInfo keyTypeInfo : TypeInfo.values()) {
                    for (TypeInfo valueTypeInfo : TypeInfo.values()) {
                        String generatedClassName = replaceSequentially(
                                CLASS_NAME_PATTERN, className, keyTypeInfo.typeName, valueTypeInfo.typeName);
                        File generatedTarget = new File(target.getParent(), generatedClassName + ".java");
                        if (!generatedTarget.exists()) {
                            generateSourceCode(source, generatedTarget, keyTypeInfo, valueTypeInfo);
                        }
                    }
                }
                return;
            }
            throw new IllegalStateException("There should be only 1 or 2 type names in the class name");
        }
    }

    private String replaceSequentially(Pattern pattern, String initialString, String... replacements) {
        String result = initialString;
        for (String replacement : replacements) {
            result = pattern.matcher(result).replaceFirst(replacement);
        }
        return result;
    }

    private void generateSourceCode(File source, File target, TypeInfo... typeInfos) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(source));
        StringBuilder result = new StringBuilder();
        try {
            StringBuilder currentToken = new StringBuilder();
            for (int charCode = reader.read(); charCode != -1; charCode = reader.read()) {
                char c = (char) charCode;
                if (isTokenDelimiter(c)) {
                    String transformed = transformToken(currentToken.toString(), typeInfos);
                    result.append(transformed);
                    currentToken.setLength(0);
                    result.append(c);
                } else {
                    currentToken.append(c);
                }
            }
            result.append(transformToken(currentToken.toString(), typeInfos));
        } catch (UnsupportedTypeException e) {
            return;
        } finally {
            reader.close();
        }
        getLog().info("Generating " + source.getName() + " to " + target.getName());
        PrintWriter writer = new PrintWriter(target);
        writer.print(result);
        writer.close();
    }

    private boolean isTokenDelimiter(char c) {
        return Character.isWhitespace(c) || TOKEN_DELIMITERS.contains(Character.toString(c));
    }

    private String transformToken(String s, TypeInfo... typeInfos) throws UnsupportedTypeException {
        if (s.contains("/*T*/")) {
            if (typeInfos.length != 1) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 1 TypeInfo's were passed to transform /*T*/");
            }
            s = PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].primitiveName);
        }
        if (s.contains("/*C*/")) {
            if (typeInfos.length != 1) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 1 TypeInfo's were passed to transform /*C*/");
            }
            if (typeInfos[0] == TypeInfo.BOOLEAN) {
                throw new UnsupportedTypeException("/*C*/ cannot be boolean");
            }
            s = COMPARABLE_PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].primitiveName);
        }
        if (s.contains("/*W*/")) {
            if (typeInfos.length != 1) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 1 TypeInfo's were passed to transform /*W*/");
            }
            s = WRAPPER_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].wrapperName);
        }
        if (s.contains("/*WC*/")) {
            if (typeInfos.length != 1) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 1 TypeInfo's were passed to transform /*WC*/");
            }
            if (typeInfos[0] == TypeInfo.BOOLEAN) {
                throw new UnsupportedTypeException("/*WC*/ cannot be boolean");
            }
            s = COMPARABLE_WRAPPER_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].wrapperName);
        }

        if (s.contains("/*K*/")) {
            if (typeInfos.length != 2) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 2 TypeInfo's were passed to transform /*K*/");
            }
            s = KEY_PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].primitiveName);
        }
        if (s.contains("/*KC*/")) {
            if (typeInfos.length != 2) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 2 TypeInfo's were passed to transform /*KC*/");
            }
            if (typeInfos[0] == TypeInfo.BOOLEAN) {
                throw new UnsupportedTypeException("/*KC*/ cannot be boolean");
            }
            s = COMPARABLE_KEY_PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[0].primitiveName);
        }
        if (s.contains("/*V*/")) {
            if (typeInfos.length != 2) {
                throw new IllegalArgumentException(
                        typeInfos.length + " instead of 2 TypeInfo's were passed to transform /*V*/");
            }
            s = VALUE_PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfos[1].primitiveName);
        }

        if (s.startsWith("_")) {
            s = s.substring(1);
            Matcher matcher = CLASS_NAME_PATTERN.matcher(s);
            StringBuffer sb = new StringBuffer();
            int i = 0;
            while (matcher.find()) {
                matcher.appendReplacement(sb, typeInfos[i++].typeName);
            }
            matcher.appendTail(sb);
            if (i != typeInfos.length) {
                throw new IllegalArgumentException("Wrong number of TypeInfo's were passed to transformToken()");
            }
            s = sb.toString();
        }
        return s;
    }
}
