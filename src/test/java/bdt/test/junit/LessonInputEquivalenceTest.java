package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import bdt.controller.LessonController;
import bdt.controller.ScheduleException;
import bdt.model.Lesson;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 5. Класи еквівалентності для даних, які вводить керівник секції під час обліку
 * фактичного заняття: дата, тема, учасник, якому ставиться відмітка.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LessonInputEquivalenceTest {
    private static final LocalDate PAST_MONDAY = LocalDate.of(2026, 11, 9);
    private static final LocalDate FUTURE_MONDAY = LocalDate.of(2026, 11, 16);

    private TestData.MemoryLessonStore store;
    private LessonController controller;

    @Before
    public void openController() {
        store = new TestData.MemoryLessonStore(false);
        controller = new LessonController(store, TestData.mondaySchedule(), TestData.fixedClock());
    }

    // ---- дата заняття ----

    @Test
    public void ec27_pastDate_opensLesson() {
        Lesson lesson = controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        assertEquals(3, lesson.getMarks().size());
    }

    @Test
    public void ec28_futureDate_isRejected() {
        ScheduleException ex = assertThrows(ScheduleException.class,
                () -> controller.openLesson(TestData.DANCE.getId(), FUTURE_MONDAY));
        assertEquals("Не можна зареєструвати заняття на майбутню дату.", ex.getMessage());
    }

    @Test
    public void ec29_missingDate_isRejected() {
        assertThrows(ScheduleException.class, () -> controller.openLesson(TestData.DANCE.getId(), null));
    }

    // ---- тема заняття ----

    @Test
    public void ec30_textTopic_isSavedTrimmed() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        controller.setTopic("  Основні кроки польки ");
        assertEquals("Основні кроки польки", controller.getLesson().getTopic());
    }

    @Test
    public void ec31_emptyTopic_isRejected() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        ScheduleException ex = assertThrows(ScheduleException.class, () -> controller.setTopic(""));
        assertEquals("Вкажіть тему заняття.", ex.getMessage());
    }

    @Test
    public void ec32_blankTopic_isRejected() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        assertThrows(ScheduleException.class, () -> controller.setTopic("    "));
    }

    @Test
    public void ec33_missingTopic_isRejected() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        assertThrows(ScheduleException.class, () -> controller.setTopic(null));
    }

    // ---- учасник, якому ставиться відмітка ----

    @Test
    public void ec34_participantOfSection_canBeMarkedAbsent() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        controller.setTopic("Основні кроки польки");
        controller.markAttendance(12, false);
        assertEquals(2, controller.closeLesson());
        assertEquals(1, store.getSavedCount());
    }

    @Test
    public void ec35_participantOutsideSection_isRejected() {
        controller.openLesson(TestData.DANCE.getId(), PAST_MONDAY);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> controller.markAttendance(99, false));
        assertEquals("Учасник не записаний до секції.", ex.getMessage());
    }
}
