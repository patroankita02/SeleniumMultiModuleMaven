package Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YAMLReader
{
    private Map<String, Object> testData;
    private static ThreadLocal<YAMLReader> tlYAMLDataReader = new ThreadLocal<>();

    //Constructor for YAML Reader
    public YAMLReader(String filePath)
    {
        load_yaml_file(filePath);
    }

    private void load_yaml_file(String filePath)
    {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath))
        {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            testData = yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading YAML file: " + e.getMessage(), e);
        }
    }

    public Object getData(String key)
    {
        return testData.get(key);
    }

    public Map<String, Object> getAllData()
    {
        return testData;
    }

    /**
     * This method is used to load the yaml file from respurces
     * @return it return yaml object.
     */
//    public YAMLReader int_yaml(String yamlFilePath)
//    {
//        YAMLReader yamlDataReader = new YAMLReader(yamlFilePath);
//        tlYAMLDataReader.set(yamlDataReader);
//        System.out.println("Get YAML data from: " + yamlFilePath);
//
//        System.out.println("Load YAML data contents:");
//        for (Map.Entry<String, Object> entry : yamlDataReader.getAllData().entrySet())
//        {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
//        return getYamlData();
//    }
//
//    //Get the YAMLDataReader and made it thread safe for parallel test
//    public static YAMLReader getYamlData()
//    {
//        return tlYAMLDataReader.get();
//    }
}
