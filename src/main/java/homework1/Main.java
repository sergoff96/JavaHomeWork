package homework1;

import homework1.runner.TestRunner;
import homework1.tests.ExampleTests;

public class Main {
    public static void main(String[] args) {
        TestRunner.runTests(ExampleTests.class);
    }
}

