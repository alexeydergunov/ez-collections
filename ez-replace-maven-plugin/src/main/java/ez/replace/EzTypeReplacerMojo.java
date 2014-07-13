package ez.replace;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
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

    private static final String TOKEN_DELIMITERS = "[](){}<>.,;";

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("_.*?_");
    private static final Pattern PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*T\\*/.*/\\*T\\*/");
    private static final Pattern WRAPPER_TYPE_PATTERN = Pattern.compile("/\\*W\\*/.*/\\*W\\*/");

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
            for (TypeInfo typeInfo : TypeInfo.values()) {
                String typeName = typeInfo.typeName;
                String generatedClassName = CLASS_NAME_PATTERN.matcher(className).replaceAll(typeName);
                File generatedTarget = new File(target.getParent(), generatedClassName + ".java");
                if (!generatedTarget.exists()) {
                    generateSourceCode(source, generatedTarget, typeInfo);
                }
            }
        }
    }

    private void generateSourceCode(File source, File target, TypeInfo typeInfo) throws IOException {
        getLog().info("Generating " + source + " to " + target);
        BufferedReader reader = new BufferedReader(new FileReader(source));
        PrintWriter writer = new PrintWriter(target);
        try {
            StringBuilder currentToken = new StringBuilder();
            for (int charCode = reader.read(); charCode != -1; charCode = reader.read()) {
                char c = (char) charCode;
                if (isTokenDelimiter(c)) {
                    String transformed = transformToken(currentToken.toString(), typeInfo);
                    writer.print(transformed);
                    currentToken.setLength(0);
                    writer.print(c);
                } else {
                    currentToken.append(c);
                }
            }
            writer.print(transformToken(currentToken.toString(), typeInfo));
        } finally {
            reader.close();
            writer.close();
        }
    }

    private boolean isTokenDelimiter(char c) {
        return Character.isWhitespace(c) || TOKEN_DELIMITERS.contains(Character.toString(c));
    }

    private String transformToken(String s, TypeInfo typeInfo) {
        if (s.contains("/*T*/")) {
            s = PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfo.primitiveName);
        }
        if (s.contains("/*W*/")) {
            s = WRAPPER_TYPE_PATTERN.matcher(s).replaceAll(typeInfo.wrapperName);
        }
        if (s.startsWith("_")) {
            s = s.substring(1);
            s = CLASS_NAME_PATTERN.matcher(s).replaceAll(typeInfo.typeName);
        }
        return s;
    }
}
