package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import bdt.controller.ScheduleController;
import bdt.dao.DataException;
import bdt.dao.LessonRepository;
import bdt.dao.RoomCatalog;
import bdt.dao.ScheduleRepository;
import bdt.dao.SectionCatalog;
import bdt.db.Database;
import bdt.model.Lesson;
import bdt.model.ScheduleEntry;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Надійність збереження фактичного заняття: якщо під час запису відміток відвідування виникає помилка,
 * транзакція відкочується і в базі даних не залишається ні заняття, ні частини відміток.
 */
public class LessonSaveRollbackTest {
    /** Секція карате (№ 10) займається по п’ятницях; заняття 06.11.2026 у тестових даних не зареєстроване. */
    private static final int KARATE = 10;
    private static final int FRIDAY = 5;
    private static final LocalDate DATE = LocalDate.of(2026, 11, 6);

    private TestDatabase testDb;
    private LessonRepository repository;
    private ScheduleEntry entry;

    @Before
    public void createRepository() {
        testDb = new TestDatabase();
        Database db = testDb.database();
        ScheduleController schedule =
                new ScheduleController(new SectionCatalog(db), new RoomCatalog(db), new ScheduleRepository(db));
        entry = schedule.getSchedule().findEntry(KARATE, FRIDAY);
        repository = new LessonRepository(db);
    }

    @After
    public void closeDatabase() {
        testDb.close();
    }

    @Test
    public void failureWhileSavingAttendance_rollsBackLesson() {
        int lessonsBefore = testDb.count("SELECT COUNT(*) FROM lesson");
        int marksBefore = testDb.count("SELECT COUNT(*) FROM attendance");
        Lesson lesson = new Lesson(entry, DATE);
        lesson.setTopic("Відпрацювання базової техніки");
        lesson.addParticipant(1, "Учасник із бази даних");
        lesson.addParticipant(9999, "Учасник, якого немає в базі даних");   // порушить зовнішній ключ

        assertThrows(DataException.class, () -> repository.save(lesson));

        assertEquals("заняття не має зберегтися", lessonsBefore, testDb.count("SELECT COUNT(*) FROM lesson"));
        assertEquals("відмітки не мають зберегтися", marksBefore, testDb.count("SELECT COUNT(*) FROM attendance"));
    }

    @Test
    public void afterFailedSave_nextSaveSucceeds() {
        Lesson broken = new Lesson(entry, DATE);
        broken.setTopic("Відпрацювання базової техніки");
        broken.addParticipant(9999, "Учасник, якого немає в базі даних");
        assertThrows(DataException.class, () -> repository.save(broken));

        Lesson correct = new Lesson(entry, DATE);
        correct.setTopic("Відпрацювання базової техніки");
        correct.addParticipant(1, "Учасник із бази даних");
        repository.save(correct);

        assertEquals(1, testDb.count("SELECT COUNT(*) FROM lesson WHERE section_id = ? AND lesson_date = ?",
                KARATE, java.sql.Date.valueOf(DATE)));
    }
}
