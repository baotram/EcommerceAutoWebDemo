package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LangReader {
    private static final Properties properties = new Properties();

    static {
        // Lấy tham số 'lang', mặc định là 'en'
        String lang = System.getProperty("lang", "en").toLowerCase();
        String fileName = "lang/" + lang + ".properties";
        
        try (InputStream input = LangReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                // Đọc file properties dưới định dạng UTF-8 để hỗ trợ ký tự Tiếng Việt
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
