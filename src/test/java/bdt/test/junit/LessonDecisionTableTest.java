package bdt.test.junit;

import static org.junit.Assert.assertEquals;

import bdt.controller.LessonController;
import bdt.controller.ScheduleException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 3. Таблиця рішень прецеденту «Облік фактичного заняття».
 * Тест параметризований: кожен набір параметрів – один стовпець повної таблиці рішень (16 стовпців).
 *
 * Умови: C1 – дата не пізніша за поточну; C2 – за розкладом у секції є заняття в цей день тижня;
 * C3 – заняття на цю дату ще не зареєстроване; C4 – тему заняття вказано.
 */
@RunWith(Parameterized.class)
public class LessonDecisionTableTest {
    static final String A1_FUTURE_DATE = "Не можна зареєструвати заняття на майбутню дату.";
    static final String A2_NO_LESSON_THAT_DAY = "За розкладом у секції немає заняття в цей день тижня.";
    static final String A3_ALREADY_REGISTERED = "Заняття секції на цю дату вже зареєстроване.";
    static final String A4_NO_TOPIC = "Вкажіть тему заняття.";
    static final String A5_SAVED = "Заняття збережено";

    /** Рядки «Дія» повної таблиці рішень: яка дія виконується в кожному зі стовпців 0–15. */
    private static final String[] ACTIONS = {
        A1_FUTURE_DATE, A1_FUTURE_DATE, A1_FUTURE_DATE, A1_FUTURE_DATE,                 // стовпці 0–3
        A1_FUTURE_DATE, A1_FUTURE_DATE, A1_FUTURE_DATE, A1_FUTURE_DATE,                 // стовпці 4–7
        A2_NO_LESSON_THAT_DAY, A2_NO_LESSON_THAT_DAY, A2_NO_LESSON_THAT_DAY, A2_NO_LESSON_THAT_DAY, // 8–11
        A3_ALREADY_REGISTERED, A3_ALREADY_REGISTERED,                                   // стовпці 12–13
        A4_NO_TOPIC,                                                                    // стовпець 14
        A5_SAVED,                                                                       // стовпець 15
    };

    private final boolean notFuture;
    private final boolean inSchedule;
    private final boolean notRegistered;
    private final boolean topicGiven;
    private final String expectedAction;

    public LessonDecisionTableTest(String column, boolean c1, boolean c2, boolean c3, boolean c4, String action) {
        this.notFuture = c1;
        this.inSchedule = c2;
        this.notRegistered = c3;
        this.topicGiven = c4;
        this.expectedAction = action;
    }

    /** Повна таблиця рішень: 2^4 = 16 комбінацій умов. */
    @Parameters(name = "{0}")
    public static Collection<Object[]> columns() {
        List<Object[]> columns = new ArrayList<>();
        for (int n = 0; n < 16; n++) {
            boolean c1 = (n & 8) != 0;
            boolean c2 = (n & 4) != 0;
            boolean c3 = (n & 2) != 0;
            boolean c4 = (n & 1) != 0;
            String name = String.format("стовпець %2d: C1=%d C2=%d C3=%d C4=%d", n, bit(c1), bit(c2), bit(c3), bit(c4));
            columns.add(new Object[]{name, c1, c2, c3, c4, ACTIONS[n]});
        }
        return columns;
    }

    private static int bit(boolean value) {
        return value ? 1 : 0;
    }

    @Test
    public void column() {
        TestData.MemoryLessonStore store = new TestData.MemoryLessonStore(!notRegistered);
        LessonController controller =
                new LessonController(store, TestData.mondaySchedule(), TestData.fixedClock());

        String actualAction = registerLesson(controller);

        assertEquals("дія", expectedAction, actualAction);
        assertEquals("кількість збережених занять", A5_SAVED.equals(expectedAction) ? 1 : 0, store.getSavedCount());
    }

    /** Виконує прецедент і повертає виконану дію: текст повідомлення про помилку або «Заняття збережено». */
    private String registerLesson(LessonController controller) {
        try {
            controller.openLesson(TestData.DANCE.getId(), lessonDate());
            controller.setTopic(topicGiven ? "Основні кроки польки" : "   ");
            controller.closeLesson();
            return A5_SAVED;
        } catch (ScheduleException ex) {
            return ex.getMessage();
        }
    }

    /** Секція «Танці» займається по понеділках; «сьогодні» – четвер 12.11.2026. */
    private LocalDate lessonDate() {
        if (notFuture) {
            return inSchedule ? LocalDate.of(2026, 11, 9) : LocalDate.of(2026, 11, 10);   // минулі Пн / Вт
        }
        return inSchedule ? LocalDate.of(2026, 11, 16) : LocalDate.of(2026, 11, 17);      // майбутні Пн / Вт
    }
}
