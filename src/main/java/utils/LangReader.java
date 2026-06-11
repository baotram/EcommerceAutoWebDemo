package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LangReader {
    private static final Properties properties = new Properties();

    static {
        // Get 'lang' parameter, default is 'en'
        String lang = System.getProperty("lang", "en").toLowerCase();
        String fileName = "lang/" + lang + ".properties";
        
        try (InputStream input = LangReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                // Read the properties file in UTF-8 format to support Vietnamese characters
                properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
                System.out.println("Loaded language package: " + fileName);
            } else {
                System.err.println("Warning: Language file " + fileName + " not found. Falling back to lang/en.properties");
                try (InputStream fallbackInput = LangReader.class.getClassLoader().getResourceAsStream("lang/en.properties")) {
                    if (fallbackInput != null) {
                        properties.load(new InputStreamReader(fallbackInput, StandardCharsets.UTF_8));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load language file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
