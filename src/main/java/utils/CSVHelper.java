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
                        continue; //Skip the header row
                    }


                    // Use split with a limit of -1 to retain empty fields at the end or in the middle
                    // This regex ignores commas located inside double quotes
                    String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    // Trim whitespace or remove double quotes (if any)
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
