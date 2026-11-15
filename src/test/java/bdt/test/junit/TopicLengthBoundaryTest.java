package bdt.test.junit;

import static bdt.test.junit.TestDatabase.repeat;
import static org.junit.Assert.assertEquals;

import bdt.controller.LessonController;
import bdt.controller.ScheduleController;
import bdt.dao.DataException;
import bdt.dao.LessonRepository;
import bdt.dao.RoomCatalog;
import bdt.dao.ScheduleRepository;
import bdt.dao.SectionCatalog;
import bdt.db.Database;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 6. Гранична довжина теми заняття (стовпець lesson.topic, VARCHAR(120)).
 * Тест інтеграційний: контролер, репозиторій і база даних H2 в оперативній пам’яті.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopicLengthBoundaryTest {
    /** Секція карате (№ 10) займається по п’ятницях; заняття 06.11.2026 у тестових даних не зареєстроване. */
    private static final int KARATE = 10;
    private static final LocalDate FRIDAY = LocalDate.of(2026, 11, 6);

    private TestDatabase testDb;
    private LessonController controller;

    @Before
    public void createControllers() {
        testDb = new TestDatabase();
        Database db = testDb.database();
        ScheduleController schedule =
                new ScheduleController(new SectionCatalog(db), new RoomCatalog(db), new ScheduleRepository(db));
        controller = new LessonController(new LessonRepository(db), schedule.getSchedule(), TestData.fixedClock());
    }

    @After
    public void closeDatabase() {
        testDb.close();
    }

    /** Реєструє заняття з темою заданої довжини; повертає «збережено» або «відхилено». */
    private String saveLessonWithTopic(int length) {
        controller.openLesson(KARATE, FRIDAY);
        controller.setTopic(repeat('т', length));
        try {
            controller.closeLesson();
            return "збережено";
        } catch (DataException ex) {
            return "відхилено";
        }
    }

    private int savedLessons() {
        return testDb.count("SELECT COUNT(*) FROM lesson WHERE section_id = ? AND lesson_date = ?",
                KARATE, java.sql.Date.valueOf(FRIDAY));
    }

    @Test
    public void bv79_topic119Characters_isSaved() {
        assertEquals("збережено", saveLessonWithTopic(119));
        assertEquals(1, savedLessons());
    }

    @Test
    public void bv80_topic120Characters_isSaved() {
        assertEquals("збережено", saveLessonWithTopic(120));
        assertEquals(1, savedLessons());
    }

    @Test
    public void bv81_topic121Characters_isRejectedAndNothingIsSaved() {
        assertEquals("відхилено", saveLessonWithTopic(121));
        assertEquals("транзакцію має бути відкочено", 0, savedLessons());
        assertEquals("відмітки відвідування не мають зберегтися", 0,
                testDb.count("SELECT COUNT(*) FROM attendance a JOIN lesson l ON l.lesson_id = a.lesson_id "
                        + "WHERE l.section_id = ? AND l.lesson_date = ?", KARATE, java.sql.Date.valueOf(FRIDAY)));
    }
}
