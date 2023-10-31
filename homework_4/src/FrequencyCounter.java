import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FrequencyCounter {
    // Создаем метод, который принимает имя файла в виде строки и
    // возвращает текст из файла в виде строки
    public static String readTextFromFile(String filename) {
        // Объявляем и инициализируем переменную для хранения текста из файла
        String textContainer = "";
        // Так как нам отсутствие ошибок при работе с файлами не гарантировано,
        // используем блок try-catch для обработки возможных исключений
        try {
            // Создаем объект BufferedReader для чтения из файла
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            // Читаем текст из файла построчно и добавляем его в переменную контейнер текста
            String line = reader.readLine();
            while (line != null) {
                textContainer += line;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
        return textContainer;
    }

    // Создаем метод - счетчик частоты появления каждого символа в виде ХешМапа
    public static HashMap<Character, Integer> countFreq(String text) {
        // Объявляем и инициализируем переменную для хранения частоты появления каждого
        // символа
        HashMap<Character, Integer> freqMap = new HashMap<>();
        // Преобразуем текст в массив символов
        char[] characters = text.toCharArray();
        // Проходим по массиву символов и подсчитываем частоту появления каждого
        // символа, исключая пробелы и переводы строк
        for (char c : characters) {
            if (c != ' ' && c != '\n') {
                // Если символ уже есть в карте частот, то увеличиваем его значение на 1
                if (freqMap.containsKey(c)) {
                    freqMap.put(c, freqMap.get(c) + 1);
                } else {
                    // Иначе добавляем символ в карту частот с значением 1
                    freqMap.put(c, 1);
                }
            }
        }
        return freqMap;
    }

    // Создаем метод для сортировки ХешМапа частот
    public static TreeMap<Integer, Character> sortFreq(HashMap<Character, Integer> freqMap) {
        // Объявляем и инициализируем переменную для хранения отсортированной частоты
        // появления каждого символа
        TreeMap<Integer, Character> sortedFreqMap = new TreeMap<>();
        // Проходим по карте частот и добавляем пары (частота, символ) в отсортированную
        // карту частот
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            sortedFreqMap.put(entry.getValue(), entry.getKey());
        }
        return sortedFreqMap;
    }

    // Создаем метод для форматного вывода сортированного ХешМапа частот символов
    public static String formatResult(TreeMap<Integer, Character> sortedFreqMap) {
        // Объявляем и инициализируем переменную для хранения результата в виде строки
        String result = "";

        // Создаем счетчик для отслеживания количества элементов в строке
        int counter = 0;

        // Проходим по отсортированной карте частот в обратном порядке и добавляем пары
        // (символ, частота) в переменную result через запятую
        for (Map.Entry<Integer, Character> entry : sortedFreqMap.descendingMap().entrySet()) {
            // Увеличиваем счетчик на 1
            counter++;
            // Добавляем символ и его частоту в строку result с табуляцией
            result += "\t`" + entry.getValue() + "`: " + entry.getKey() + ", ";
            // Если счетчик достиг пяти, то добавляем перенос строки и обнуляем счетчик
            if (counter == 5) {
                result += "\n";
                counter = 0;
            }
        }

        // Удаляем последнюю запятую из переменной result
        result = result.substring(0, result.length() - 2);
        return result;
    }

    // Создаем метод для записи результата (отсортированного, форматированного
    // ХешМапа частот) в файл
    public static void writeFreqToMap(String result, String filename) {
        // Так как нам отсутствие ошибок при работе с файлами не гарантировано,
        // используем блок try-catch для обработки возможных исключений
        try {
            // создаем объект для записи в файл
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8");
            // записываем в файл
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            // в случае ошибки при работе с файлом, ловим исключение и отправляем сообщение
            // с ошибкой
            System.out.println("Ошибка при работе с файлом" + filename + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        // Объявляем и инициализируем переменные для имени входного и выходного файла
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        // Вызываем метод читения текста из входного файла
        String text = readTextFromFile(inputFile);

        // Вызываем метод подсчитываета частоты появления каждого
        // символа в тексте
        HashMap<Character, Integer> freqMap = countFreq(text);

        // Вызываем метод, который сортирует частоту появления каждого
        // символа по убыванию
        TreeMap<Integer, Character> sortedFreqMap = sortFreq(freqMap);

        // Форматируем строку
        String result = formatResult(sortedFreqMap);

        // Записываем результат в output файл
        writeFreqToMap(result, outputFile);

        System.out.println("Success! The program is ending...");
    }
}
