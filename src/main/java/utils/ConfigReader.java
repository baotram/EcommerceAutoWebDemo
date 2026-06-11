package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private final static String propertyFilePath = "src/test/resources/config.properties";

    public static String getValue(String key){
        properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propertyFilePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Không tìm thấy file config tại: " + propertyFilePath );
        }
        return properties.getProperty(key);
    }
}
