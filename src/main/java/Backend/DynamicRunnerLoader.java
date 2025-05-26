package Backend;

import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import javax.tools.JavaFileObject;
import javax.tools.DiagnosticCollector;

public class DynamicRunnerLoader {
    private static final String SOURCE_FOLDER = "/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/UserCreated/";
    private static final String COMPILED_FOLDER = "/home/8alyan/Projects/Vault/MazeRunnerv1/compiled_classes/";

    public static Runner compileAndLoad(String className) throws Exception {
        // Prepare folders
        File sourceFile = new File(SOURCE_FOLDER + className + ".java");
        File outputDir = new File(COMPILED_FOLDER);
        if (!outputDir.exists()) outputDir.mkdirs();

        // Set up compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("No compiler found. Are you using a JDK?");

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(sourceFile);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                Arrays.asList("-d", COMPILED_FOLDER),
                null,
                compilationUnits
        );

        boolean success = task.call();
        fileManager.close();

        if (!success) {
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println(diagnostic);
            }
            throw new RuntimeException("Compilation failed.");
        }

        // Load class dynamically
        try (URLClassLoader classLoader = new URLClassLoader(
                new URL[]{outputDir.toURI().toURL()},
                Runner.class.getClassLoader())) {

            Class<?> loadedClass = classLoader.loadClass("Backend.UserCreated."+className);
            Object instance = loadedClass.getDeclaredConstructor().newInstance();

            if (!(instance instanceof Runner)) {
                throw new IllegalArgumentException("Class does not implement Runner interface");
            }

            return (Runner) instance;
        }
    }
}