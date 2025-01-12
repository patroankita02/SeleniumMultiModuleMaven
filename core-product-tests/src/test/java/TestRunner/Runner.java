package TestRunner;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;
import io.cucumber.testng.FeatureWrapper;

import javax.lang.model.element.Modifier;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
    private static final String FEATURE_DIRECTORY = "src/test/java/Feature";

    @Factory
    public Object[] generateRunners() throws IOException
    {
        // Fetch tags dynamically from testng.xml or environment variable
        String tags = System.getProperty("cucumber.tags", "@regression"); // Default to @smoke if no tag is provided
        System.out.println("Running with Tags: " + tags);

        List<File> featureFiles = getFeatureFiles(FEATURE_DIRECTORY);
        System.out.println("Feature files found: " + featureFiles);

        return featureFiles.stream()
                .map(featureFile -> generateRunnerForFeature(featureFile, tags))
                .toArray(Object[]::new);
    }

    private Object generateRunnerForFeature(File featureFile, String tags) {
        String featureName = featureFile.getName().replace(".feature", "");
        String className = featureName + "Runner";
        Path outputPath = Paths.get(System.getProperty("java.io.tmpdir"), "dynamic_runners", "com", "example", "runners");
        Path runnerFilePath = outputPath.resolve(className + ".java");

        try {
            Files.createDirectories(outputPath);
            // Dynamically bind CucumberOptions with the provided tags
            AnnotationSpec cucumberOptionsAnnotation = AnnotationSpec.builder(CucumberOptions.class)
                    .addMember("features", "$S", featureFile.getPath())
                    .addMember("glue", "$S", "StepDefinition")
                    .addMember("plugin", "$S", "json:target/cucumber-json-reports/" + featureName + ".json")
                    .addMember("plugin", "$S", "html:target/cucumber-reports/" + featureName + "-report.html") // Dynamic HTML report path
                    .addMember("tags", "$S", tags) // Pass tags dynamically
                    .build();

            // Create the DataProvider method for parallel execution
            MethodSpec scenariosMethod = MethodSpec.methodBuilder("scenarios")
                    .addAnnotation(AnnotationSpec.builder(DataProvider.class).build()) // Add @DataProvider
                    .addModifiers(Modifier.PUBLIC)
                    .returns(Object[][].class)
                    .addStatement("return super.scenarios()") // Call super implementation
                    .build();

            // Build the runner class
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(AbstractTestNGCucumberTests.class)
                    .addAnnotation(cucumberOptionsAnnotation)
                    .addMethod(scenariosMethod);

            // Write the class file
            JavaFile javaFile = JavaFile.builder("com.example.runners", classBuilder.build()).build();

            try (BufferedWriter writer = Files.newBufferedWriter(runnerFilePath, StandardCharsets.UTF_8)) {
                writer.write(javaFile.toString());
            }

            // Compile the generated class
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            String[] compileOptions = new String[]{
                    "-d", outputPath.getParent().toString(),
                    "-classpath", System.getProperty("java.class.path"),
                    runnerFilePath.toString()
            };
            compiler.run(null, null, null, compileOptions);

            // Load the compiled class
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{outputPath.getParent().toUri().toURL()});
            Class<?> runnerClass = Class.forName("com.example.runners." + className, true, classLoader);
            return runnerClass.getConstructor().newInstance();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate runner class for feature file: " + featureFile.getName(), e);
        }
    }

    private List<File> getFeatureFiles(String directoryPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".feature"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error fetching feature files", e);
        }
    }
}
