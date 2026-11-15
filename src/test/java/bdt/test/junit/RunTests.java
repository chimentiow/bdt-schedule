package bdt.test.junit;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Запуск тестів JUnit з протоколом у вигляді таблиці «клас – тест – результат».
 * Без аргументів запускає всі набори тестів; аргументи – короткі назви класів тестів.
 */
public final class RunTests {
    private static final Class<?>[] ALL = {
        LessonDecisionTableTest.class,
        AddEntryDecisionTableTest.class,
    };

    private RunTests() {
    }

    public static void main(String[] args) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (args.length == 0) {
            classes.addAll(List.of(ALL));
        }
        for (String name : args) {
            classes.add(Class.forName("bdt.test.junit." + name));
        }
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TableListener());
        Result result = junit.run(classes.toArray(new Class<?>[0]));
        System.out.println();
        System.out.printf("Усього тестів: %d, пройдено: %d, не пройдено: %d, час: %.2f с%n", result.getRunCount(),
                result.getRunCount() - result.getFailureCount(), result.getFailureCount(),
                result.getRunTime() / 1000.0);
        for (Failure failure : result.getFailures()) {
            System.out.println("НЕ ПРОЙДЕНО: " + failure.getDescription().getMethodName() + " – "
                    + failure.getMessage());
        }
        if (!result.wasSuccessful()) {
            System.exit(1);
        }
    }

    /** Друкує рядок протоколу для кожного тесту. */
    private static class TableListener extends RunListener {
        private String currentClass = "";
        private boolean failed;
        private int number;

        @Override
        public void testStarted(Description description) {
            String className = description.getTestClass().getSimpleName();
            if (!className.equals(currentClass)) {
                currentClass = className;
                System.out.println();
                System.out.println("=== " + className + " ===");
            }
            failed = false;
        }

        @Override
        public void testFailure(Failure failure) {
            failed = true;
        }

        @Override
        public void testFinished(Description description) {
            number++;
            System.out.printf("%3d | %-78s | %s%n", number, description.getMethodName(), failed ? "FAIL" : "PASS");
        }
    }
}
