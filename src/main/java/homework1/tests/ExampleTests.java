package homework1.tests;

import homework1.annotations.*;

public class ExampleTests {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("-Before Suite executed");
    }

    @BeforeTest
    public static void beforeTest() {
        System.out.println("--Before each Test executed");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("---Test 1 executed");
    }

    @Test(priority = 7)
    public void test2() {
        System.out.println("---Test 2 executed");
    }

    @Test
    @CsvSource("10,Java,20,true")
    public void testWithCsv(int a, String b, int c, boolean d) {
        System.out.printf("---CSV Test executed with args: %d, %s, %d, %b%n", a, b, c, d);
    }

    @AfterTest
    public void afterEachTest() {
        System.out.println("--After each test executed");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("-After Suite executed");
    }
}
