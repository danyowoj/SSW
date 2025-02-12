import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataFilter {
    private static String outputPath = ".";
    private static String prefix = "";
    private static boolean appendMode = false;
    private static boolean fullStats = false;
    private static boolean shortStats = false;

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        parseArguments(args, inputFiles);

        Map<String, List<String>> categorizedData = new HashMap<>();
        categorizedData.put("integers", new ArrayList<>());
        categorizedData.put("floats", new ArrayList<>());
        categorizedData.put("strings", new ArrayList<>());

        processFiles(inputFiles, categorizedData);
        writeResults(categorizedData);
        printStatistics(categorizedData);
    }

    private static void parseArguments(String[] args, List<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) outputPath = args[++i];
                    break;
                case "-p":
                    if (i + 1 < args.length) prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-f":
                    fullStats = true;
                    shortStats = false;
                    break;
                case "-s":
                    shortStats = true;
                    fullStats = false;
                    break;
                default:
                    inputFiles.add(args[i]);
            }
        }
    }

    private static void processFiles(List<String> files, Map<String, List<String>> data) {
        for (String file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    categorizeData(line, data);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при обработке файла: " + file);
            }
        }
    }

    private static void categorizeData(String line, Map<String, List<String>> data) {
        try {
            Integer.parseInt(line);
            data.get("integers").add(line);
            return;
        } catch (NumberFormatException ignored) {}

        try {
            Double.parseDouble(line);
            data.get("floats").add(line);
            return;
        } catch (NumberFormatException ignored) {}

        data.get("strings").add(line);
    }

    private static void writeResults(Map<String, List<String>> data) {
        writeToFile(data.get("integers"), "integers.txt");
        writeToFile(data.get("floats"), "floats.txt");
        writeToFile(data.get("strings"), "strings.txt");
    }

    private static void writeToFile(List<String> data, String fileName) {
        if (data.isEmpty()) return;

        String fullPath = Paths.get(outputPath, prefix + fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, appendMode))) {
            for (String entry : data) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + fullPath);
        }
    }

    private static void printStatistics(Map<String, List<String>> data) {
        if (!shortStats && !fullStats) return;

        System.out.println("=== Statistics ===");
        System.out.println("+-----------------+-----------------+-----------------+");
        System.out.println("| Type            | Count           | Details         |");
        System.out.println("+-----------------+-----------------+-----------------+");

        printStatsForType("Integers", data.get("integers"));
        printStatsForType("Floats", data.get("floats"));
        printStatsForType("Strings", data.get("strings"));
    }

    private static void printStatsForType(String type, List<String> values) {
        if (values.isEmpty()) return;
        System.out.printf("| %-15s | %-15d ", type, values.size());

        if (fullStats && !type.equals("Strings")) {
            List<Double> numbers = values.stream().map(Double::parseDouble).toList();
            double min = Collections.min(numbers);
            double max = Collections.max(numbers);
            double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
            double avg = sum / numbers.size();
            System.out.printf("| Min: %-10.2f |%n", min);
            System.out.printf("| %-15s | %-15s | Max: %-10.2f |%n", "", "", max);
            System.out.printf("| %-15s | %-15s | Sum: %-10.2f |%n", "", "", sum);
            System.out.printf("| %-15s | %-15s | Avg: %-10.2f |%n", "", "", avg);
        } else if (fullStats) {
            int minLength = values.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = values.stream().mapToInt(String::length).max().orElse(0);
            System.out.printf("| Min Len: %-6d |%n", minLength);
            System.out.printf("| %-15s | %-15s | Max Len: %-6d |%n", "", "", maxLength);
        } else {
            System.out.printf("| %-15s |%n", "");
        }
        System.out.println("+-----------------+-----------------+-----------------+");
    }
}
