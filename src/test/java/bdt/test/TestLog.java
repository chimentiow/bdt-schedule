package bdt.test;

/** Протокол тестування: фіксує очікуваний і фактичний результат кожного тестового варіанта. */
public final class TestLog {
    private static int passed;
    private static int failed;

    private TestLog() {
    }

    public static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.printf("%-8s | %-58s | %-28s | %-28s | %s%n", "ID", "Тестовий варіант", "Очікувано", "Фактично", "Результат");
    }

    public static void check(String id, String description, Object expected, Object actual) {
        boolean ok = String.valueOf(expected).equals(String.valueOf(actual));
        if (ok) passed++; else failed++;
        System.out.printf("%-8s | %-58s | %-28s | %-28s | %s%n", id, description, expected, actual, ok ? "PASS" : "FAIL");
    }

    /** Виконує дію і повертає текст винятку або "OK". */
    public static String run(Runnable action) {
        try {
            action.run();
            return "OK";
        } catch (RuntimeException e) {
            return e.getClass().getSimpleName();
        }
    }

    public static void summary() {
        System.out.println();
        System.out.println("Усього тестів: " + (passed + failed) + ", пройдено: " + passed + ", не пройдено: " + failed);
    }

    public static int failed() { return failed; }
}
