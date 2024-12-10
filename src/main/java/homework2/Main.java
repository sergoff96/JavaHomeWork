package homework2;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        // 1. Удаление дубликатов
        List<Integer> list = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        List<Integer> noDuplicates = list.stream().distinct().toList();

        // 2. Найти 3-е наибольшее число
        int thirdMax = list.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElseThrow();

        // 3. Найти 3-е наибольшее уникальное число
        int thirdUniqueMax = list.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElseThrow();

        // 4. Список имен 3 самых старших инженеров
        List<Employee> employees = Arrays.asList(
                new Employee("Иван", 30, "Engineer"),
                new Employee("Александр", 40, "Manager"),
                new Employee("Сергей", 50, "Engineer"),
                new Employee("Андрей", 45, "Engineer"),
                new Employee("Игорь", 13, "Engineer"));
        List<String> top3Engineers = employees.stream()
                .filter(e -> "Engineer".equals(e.position))
                .sorted(Comparator.comparingInt((Employee e) -> e.age).reversed())
                .limit(3)
                .map(e -> e.name)
                .toList();

        // 5. Средний возраст инженеров
        double averageAge = employees.stream()
                .filter(e -> "Engineer".equals(e.position))
                .mapToInt(e -> e.age)
                .average().orElse(0);

        // 6. Самое длинное слово
        List<String> words = Arrays.asList("Слово", "СамоеДлинноеСлово", "СловоДлиннее");
        String longestWord = words.stream().max(Comparator.comparingInt(String::length)).orElse("");

        // 7. Хеш-мапа слов с частотой
        String input = "слово другоеСлово третьеСлово слово слово другоеСлово третьеСлово другоеСлово";
        Map<String, Long> wordFrequency = Arrays.stream(input.split(" "))
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        // 8. Строки в порядке увеличения длины
        List<String> sortedWords = words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .toList();

        // 9. Найти самое длинное слово среди всех строк в массиве
        String[] lines = {"СамоеДлинноеСлово Слово Слово", "раз два тридцать"};
        String longestInArray = Arrays.stream(lines)
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        // Print results for demonstration
        System.out.println("No Duplicates: " + noDuplicates);
        System.out.println("3rd Max: " + thirdMax);
        System.out.println("3rd Unique Max: " + thirdUniqueMax);
        System.out.println("Top 3 Engineers: " + top3Engineers);
        System.out.println("Average Age: " + averageAge);
        System.out.println("Longest Word: " + longestWord);
        System.out.println("Word Frequency: " + wordFrequency);
        System.out.println("Sorted Words: " + sortedWords);
        System.out.println("Longest in Array: " + longestInArray);
    }
}

class Employee {
    String name;
    int age;
    String position;

    Employee(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }
}
