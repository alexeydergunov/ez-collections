package ez.replace;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.*;
import java.util.regex.Pattern;

@SuppressWarnings("UnusedDeclaration")
@Mojo(name = "replace-types")
public class EzTypeReplacerMojo extends AbstractMojo {
    private static enum TypeInfo {
        BOOLEAN("boolean", "Boolean"),
        SHORT("short", "Short"),
        CHAR("char", "Char"),
        INT("int", "Int"),
        LONG("long", "Long"),
        FLOAT("float", "Float"),
        DOUBLE("double", "Double");

        private final String primitiveName;
        private final String typeName;

        TypeInfo(String primitiveName, String typeName) {
            this.primitiveName = primitiveName;
            this.typeName = typeName;
        }
    }

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("\\$.*?\\$");
    private static final Pattern PRIMITIVE_TYPE_PATTERN = Pattern.compile("/\\*T\\*/.*/\\*T\\*/");

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
            FileUtils.deleteDirectory(targetDirectory);
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
        for (TypeInfo typeInfo : TypeInfo.values()) {
            String typeName = typeInfo.typeName;
            String generatedClassName = CLASS_NAME_PATTERN.matcher(className).replaceAll(typeName);
            if (!className.equals(generatedClassName)) {
                generateSourceCode(source, new File(target.getParent(), generatedClassName + ".java"), typeInfo);
            }
        }
    }

    private void generateSourceCode(File source, File target, TypeInfo typeInfo) throws IOException {
        getLog().info("Generating " + source + " to " + target);
        BufferedReader reader = new BufferedReader(new FileReader(source));
        PrintWriter writer = new PrintWriter(target);
        try {
            StringBuilder currentToken = new StringBuilder();
            for (int c = reader.read(); c != -1; c = reader.read()) {
                if (Character.isWhitespace(c)) {
                    String transformed = transformToken(currentToken.toString(), typeInfo);
                    writer.print(transformed);
                    currentToken.setLength(0);
                    writer.print((char) c);
                } else {
                    currentToken.append((char) c);
                }
            }
            writer.print(transformToken(currentToken.toString(), typeInfo));
        } finally {
            reader.close();
            writer.close();
        }
    }

    private String transformToken(String s, TypeInfo typeInfo) {
        if (s.contains("/*T*/")) {
            s = PRIMITIVE_TYPE_PATTERN.matcher(s).replaceAll(typeInfo.primitiveName);
        }
        if (s.contains("$")) {
            s = CLASS_NAME_PATTERN.matcher(s).replaceAll(typeInfo.typeName);
        }
        return s;
    }
}
