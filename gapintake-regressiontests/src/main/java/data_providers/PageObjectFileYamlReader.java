package data_providers;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class PageObjectFileYamlReader {

    private final Yaml yaml;
    Map<String, Object> data;

    /**
     * Constructor for PageObjectFileYamlReader. It reads the pageObject.yml file and loads the data.
     */
    public PageObjectFileYamlReader() {
        this.yaml = new Yaml();
        String propertyFilePath = "src/test/resources/user_defined_properties/pageObject.yml";
        try (InputStream inputStream = new FileInputStream(propertyFilePath)) {
            data = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("pageObject.yml not found at " + propertyFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get the object details from the YAML file.
     *
     * @param objectKey The key of the object to get details for. It must have 4 parts separated by dots.
     * @return The object details as a string.
     * @throws IllegalArgumentException if objectKey does not have 4 parts separated by dots.
     * @throws RuntimeException if the object is not specified in the ObjectRepo.properties file.
     */
    public String getObjectDetails(String objectKey) {
        String[] obj = objectKey.split("\\.");
        if (obj.length != 4) {
            throw new IllegalArgumentException("Object key must have 4 parts separated by dots.");
        }

        Map<String, Object> currentMap = data;
        for (int i = 0; i < 3; i++) {
            currentMap = getSubMap(currentMap, obj[i], objectKey);
        }

        String locatorValue = (String) currentMap.get(obj[3]);
        if (locatorValue == null) {
            throw new IllegalArgumentException("Object not specified in the ObjectRepo.properties file: " + objectKey);
        }

        return locatorValue;
    }
    /**
     * Helper method to get a sub-map from a map.
     *
     * @param map The map to get the sub-map from.
     * @param key The key of the sub-map.
     * @param objectKey The original object key. Used for error messages.
     * @return The sub-map.
     * @throws RuntimeException if the sub-map is not found in the map.
     */
    private Map<String, Object> getSubMap(Map<String, Object> map, String key, String objectKey) {
        Map<String, Object> subMap = (Map<String, Object>) map.get(key);
        if (subMap == null) {
            throw new IllegalArgumentException("Object not specified in the ObjectRepo.yml file: " + objectKey);
        }
        return subMap;
    }

}
