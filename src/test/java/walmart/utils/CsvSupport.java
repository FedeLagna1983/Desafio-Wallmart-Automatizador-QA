package walmart.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

final class CsvSupport {

    private static final Pattern CSV_SPLIT_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    private CsvSupport() {
    }

    static <T> T findFirstByKey(
            String resourcePath,
            String key,
            int keyColumnIndex,
            String entityLabel,
            RowMapper<T> rowMapper
    ) {
        try (InputStream inputStream = CsvSupport.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String header = reader.readLine();

                if (header == null) {
                    throw new RuntimeException("CSV file is empty: " + resourcePath);
                }

                String line;
                int rowNumber = 1;

                while ((line = reader.readLine()) != null) {
                    rowNumber++;
                    String[] values = parseCsvLine(line);

                    if (values.length <= keyColumnIndex) {
                        throw new RuntimeException("Invalid CSV row at line " + rowNumber + " in " + resourcePath);
                    }

                    if (values[keyColumnIndex].equalsIgnoreCase(key.trim())) {
                        return rowMapper.map(values, rowNumber);
                    }
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + resourcePath, e);
        }

        throw new RuntimeException(entityLabel + " not found in CSV: " + key);
    }

    static String requireColumn(String[] values, int index, String columnName, String resourcePath, int rowNumber) {
        if (index >= values.length) {
            throw new RuntimeException(
                    "Missing column '" + columnName + "' at line " + rowNumber + " in " + resourcePath
            );
        }

        return values[index].trim();
    }

    private static String[] parseCsvLine(String line) {
        String[] rawValues = CSV_SPLIT_PATTERN.split(line, -1);

        for (int i = 0; i < rawValues.length; i++) {
            rawValues[i] = stripEnclosingQuotes(rawValues[i].trim());
        }

        return rawValues;
    }

    private static String stripEnclosingQuotes(String value) {
        if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }

        return value;
    }

    @FunctionalInterface
    interface RowMapper<T> {
        T map(String[] values, int rowNumber);
    }
}
