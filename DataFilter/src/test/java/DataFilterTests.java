import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class DataFilterTest {

    @TempDir
    Path tempDir;

    @Test
    void testParseArguments() {
        String[] args = {"-o", "outputDir", "-p", "pre_", "-a", "-f", "file1.txt", "file2.txt"};
        List<String> inputFiles = new ArrayList<>();
        DataFilter.parseArguments(args, inputFiles);

        assertEquals("outputDir", DataFilter.outputPath);
        assertEquals("pre_", DataFilter.prefix);
        assertTrue(DataFilter.appendMode);
        assertTrue(DataFilter.fullStats);
        assertFalse(DataFilter.shortStats);
        assertArrayEquals(new String[]{"file1.txt", "file2.txt"}, inputFiles.toArray());
    }

    @Test
    void testCategorizeData() {
        Map<String, List<String>> data = new HashMap<>();
        data.put("integers", new ArrayList<>());
        data.put("floats", new ArrayList<>());
        data.put("strings", new ArrayList<>());

        DataFilter.categorizeData("123", data);
        DataFilter.categorizeData("123.45", data);
        DataFilter.categorizeData("abc", data);

        assertEquals(1, data.get("integers").size());
        assertEquals(1, data.get("floats").size());
        assertEquals(1, data.get("strings").size());
        assertEquals("123", data.get("integers").get(0));
        assertEquals("123.45", data.get("floats").get(0));
        assertEquals("abc", data.get("strings").get(0));
    }

//    @Test
//    void testWriteToFile_EmptyData() throws IOException {
//        List<String> data = Collections.emptyList();
//        String fileName = "emptyFile.txt";
//        Path testFilePath = tempDir.resolve(fileName);
//
//        DataFilter.outputPath = tempDir.toString();
//        DataFilter.writeToFile(data, fileName);
//
//        // Проверяем, что файл не был создан
//        assertFalse(Files.exists(testFilePath), "Файл не должен создаваться, если данные пусты");
//    }
//
//    @Test
//    void testWriteToFile() throws IOException {
//        // Подготовка данных
//        List<String> data = Arrays.asList("line1", "line2", "line3");
//        String fileName = "testFile.txt";
//        Path testFilePath = tempDir.resolve(fileName);
//
//        // Устанавливаем outputPath во временную директорию
//        DataFilter.outputPath = tempDir.toString();
//
//        // Вызываем метод, который тестируем
//        DataFilter.writeToFile(data, fileName);
//
//        // Проверяем, что файл был создан
//        assertTrue(Files.exists(testFilePath), "Файл не был создан");
//
//        // Читаем файл и проверяем его содержимое
//        List<String> lines = Files.readAllLines(testFilePath);
//        assertEquals(data, lines, "Содержимое файла не совпадает с ожидаемым");
//    }
//
//    @Test
//    void testWriteToFile_AppendMode() throws IOException {
//        // Подготовка данных
//        List<String> data1 = Arrays.asList("line1", "line2");
//        List<String> data2 = Arrays.asList("line3", "line4");
//        String fileName = "appendFile.txt";
//        Path testFilePath = tempDir.resolve(fileName);
//
//        // Устанавливаем outputPath во временную директорию
//        DataFilter.outputPath = tempDir.toString();
//        DataFilter.appendMode = true;
//
//        // Первая запись
//        DataFilter.writeToFile(data1, fileName);
//        // Вторая запись (добавление)
//        DataFilter.writeToFile(data2, fileName);
//
//        // Ожидаемый результат: все строки в одном файле
//        List<String> expected = Arrays.asList("line1", "line2", "line3", "line4");
//        List<String> lines = Files.readAllLines(testFilePath);
//        assertEquals(expected, lines, "Файл должен содержать все строки");
//    }
}
