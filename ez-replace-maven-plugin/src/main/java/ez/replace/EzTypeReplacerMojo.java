package ez.replace;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.Set;
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

    // TODO rewrite this
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("_.*?_");
    private static final Pattern PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*(T)([0-9]*)\\*/.*/\\*(T)([0-9]*)\\*/");
    private static final Pattern COMPARABLE_PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*(C)([0-9]*)\\*/.*/\\*(C)([0-9]*)\\*/");
    private static final Pattern WRAPPER_TYPE_PATTERN = Pattern.compile("/\\*(W)([0-9]*)\\*/.*/\\*(W)([0-9]*)\\*/");
    private static final Pattern COMPARABLE_WRAPPER_TYPE_PATTERN = Pattern.compile("/\\*(WC)([0-9]*)\\*/.*/\\*(WC)([0-9]*)\\*/");

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

    @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"})
    @Parameter
    private Set<String> excludedClasses; // we don't generate classes that are not ready

    public void execute() throws MojoExecutionException {
        getLog().info("sourceDirectory = " + sourceDirectory);
        getLog().info("targetDirectory = " + targetDirectory);
        getLog().info("excludedClasses = " + excludedClasses);
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
                //noinspection ConstantConditions
                if (childTarget.listFiles().length == 0) {
                    //noinspection ResultOfMethodCallIgnored
                    childTarget.delete();
                }
            }
        }
    }

    private void processFile(File source, File target) throws IOException {
        String fileName = source.getName();
        String className = fileName.substring(0, fileName.length() - 5);
        if (excludedClasses.contains(className)) {
            getLog().info("Don't generate " + fileName + " since it's excluded");
            return;
        }
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

    private String transformToken(String s, TypeInfo[] typeInfos) throws UnsupportedTypeException {
        s = tryApplyPattern(s, PRIMITIVE_TYPE_PATTERN, typeInfos);
        s = tryApplyPattern(s, COMPARABLE_PRIMITIVE_TYPE_PATTERN, typeInfos);
        s = tryApplyPattern(s, WRAPPER_TYPE_PATTERN, typeInfos);
        s = tryApplyPattern(s, COMPARABLE_WRAPPER_TYPE_PATTERN, typeInfos);
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

    private String tryApplyPattern(String s, Pattern pattern, TypeInfo[] typeInfos) throws UnsupportedTypeException {
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            if (matcher.groupCount() != 4) {
                throw new IllegalArgumentException("Wrong number of groups in pattern");
            }
            String typeName1 = matcher.group(1);
            String groupName1 = matcher.group(2);
            String typeName2 = matcher.group(3);
            String groupName2 = matcher.group(4);
            if (!typeName1.equals(typeName2)) {
                throw new IllegalArgumentException("Type names in pattern are not equal");
            }
            if (!groupName1.equals(groupName2)) {
                throw new IllegalArgumentException("Type indices in pattern are not equal");
            }
            int index = groupName1.isEmpty() ? 0 : Integer.parseInt(groupName1) - 1;
            if (index < 0 || index >= typeInfos.length) {
                throw new IllegalArgumentException("Type index doesn't match the array of TypeInfo's");
            }
            String replacement = getJavaName(typeName1, typeInfos[index]);
            s = matcher.replaceAll(replacement);
        }
        return s;
    }

    private String getJavaName(String typeName, TypeInfo typeInfo) throws UnsupportedTypeException {
        if (typeName.equals("T")) {
            return typeInfo.primitiveName;
        }
        if (typeName.equals("W")) {
            return typeInfo.wrapperName;
        }
        if (typeName.equals("C")) {
            if (typeInfo == TypeInfo.BOOLEAN) {
                throw new UnsupportedTypeException("/*C*/ cannot be boolean");
            }
            return typeInfo.primitiveName;
        }
        if (typeName.equals("WC")) {
            if (typeInfo == TypeInfo.BOOLEAN) {
                throw new UnsupportedTypeException("/*WC*/ cannot be boolean");
            }
            return typeInfo.wrapperName;
        }
        throw new IllegalArgumentException("Wrong type name " + typeName);
    }
}
