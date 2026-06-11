package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static Object[][] readCSV(String filePath) {
        List<Object[]> dataList = new ArrayList<>();
        
        try (InputStream is = CSVHelper.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found in resources: " + filePath);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue; // Bỏ qua dòng tiêu đề
                    }
                    
                    // Sử dụng split với limit -1 để giữ lại các trường trống ở cuối hoặc giữa
                    // Regex này bỏ qua dấu phẩy nằm trong cặp dấu ngoặc kép
                    String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    
                    // Loại bỏ khoảng trắng thừa hoặc dấu nháy kép (nếu có)
                    for (int i = 0; i < fields.length; i++) {
                        fields[i] = fields[i].trim().replaceAll("^\"|\"$", "");
                    }
                    dataList.add(fields);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error reading CSV file " + filePath + ": " + e.getMessage());
        }
        
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }
}
