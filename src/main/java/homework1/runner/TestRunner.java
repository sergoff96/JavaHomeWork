package homework1.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import homework1.annotations.*;

public class TestRunner {

    public static void runTests(Class<?> clazz) {
        Method beforeSuite = null;
        Method afterSuite = null;
        List<Method> beforeTests = new ArrayList<>();
        List<Method> afterTests = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        // Разбор методов класса
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuite != null) {
                    throw new RuntimeException("Only one @BeforeSuite method is allowed.");
                }
                beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuite != null) {
                    throw new RuntimeException("Only one @AfterSuite method is allowed.");
                }
                afterSuite = method;
            } else if (method.isAnnotationPresent(BeforeTest.class)) {
                beforeTests.add(method);
            } else if (method.isAnnotationPresent(AfterTest.class)) {
                afterTests.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        // Сортировка методов @Test по приоритету
        testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

        try {
            // Создаем экземпляр тестового класса
            Object testInstance = clazz.getDeclaredConstructor().newInstance();

            // Выполнение @BeforeSuite
            if (beforeSuite != null) {
                invokeMethod(beforeSuite, null); // Статический метод
            }

            // Выполнение тестов
            for (Method testMethod : testMethods) {
                // Выполняем @BeforeTest
                for (Method beforeTest : beforeTests) {
                    invokeMethod(beforeTest, testInstance);
                }

                // Выполняем тестовый метод
                if (testMethod.isAnnotationPresent(CsvSource.class)) {
                    String csv = testMethod.getAnnotation(CsvSource.class).value();
                    String[] args = csv.split(",");
                    Object[] parsedArgs = parseCsvArgs(testMethod, args);
                    invokeMethod(testMethod, testInstance, parsedArgs);
                } else {
                    invokeMethod(testMethod, testInstance);
                }

                // Выполняем @AfterTest
                for (Method afterTest : afterTests) {
                    invokeMethod(afterTest, testInstance);
                }
            }

            // Выполнение @AfterSuite
            if (afterSuite != null) {
                invokeMethod(afterSuite, null); // Статический метод
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during test execution", e);
        }
    }

    // Метод для вызова методов (учитывает, статический он или нет)
    private static void invokeMethod(Method method, Object instance, Object... args) {
        try {
            if (java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                method.invoke(null, args); // Статический метод
            } else {
                method.invoke(instance, args); // Нестатический метод
            }
        } catch (Exception e) {
            throw new RuntimeException("Error invoking method: " + method.getName(), e);
        }
    }

    // Парсинг аргументов CSV
    private static Object[] parseCsvArgs(Method method, String[] args) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parsedArgs = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            String arg = args[i].trim();
            if (parameterTypes[i] == int.class) {
                parsedArgs[i] = Integer.parseInt(arg);
            } else if (parameterTypes[i] == boolean.class) {
                parsedArgs[i] = Boolean.parseBoolean(arg);
            } else if (parameterTypes[i] == String.class) {
                parsedArgs[i] = arg;
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + parameterTypes[i]);
            }
        }

        return parsedArgs;
    }
}


