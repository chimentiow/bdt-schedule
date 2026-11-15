package bdt.test;

import bdt.controller.LessonController;
import bdt.controller.ScheduleController;
import bdt.dao.LessonRepository;
import bdt.dao.RoomCatalog;
import bdt.dao.ScheduleRepository;
import bdt.dao.SectionCatalog;
import bdt.db.Database;
import bdt.model.Lesson;
import java.time.LocalDate;
import java.time.LocalTime;

/** Тестування кластера класів «контролер – репозиторії – БД H2» (інтеграційне тестування). */
public final class IntegrationDriver {
    private IntegrationDriver() {
    }

    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:h2:mem:itest;DB_CLOSE_DELAY=-1");
        db.runScript("/schema.sql");
        db.runScript("/data.sql");
        SectionCatalog sections = new SectionCatalog(db);
        ScheduleController sc = new ScheduleController(sections, new RoomCatalog(db), new ScheduleRepository(db));
        TestLog.header("Кластер ScheduleController + SectionCatalog + RoomCatalog + ScheduleRepository");
        TestLog.check("IT-1", "Розклад завантажено з БД", 20, sc.getSchedule().size());
        sc.newEntry();
        sc.selectSection(3); // гітарна студія, керівник Мельник А.В., 6 учасників
        TestLog.check("IT-2", "Кількість учасників секції 3 з БД", 6, sc.getParticipants());
        TestLog.check("IT-3", "Конфлікт із заняттям Вт 16:00–17:30", "ScheduleException",
                TestLog.run(() -> sc.setTime(2, LocalTime.of(17, 0), LocalTime.of(18, 0))));
        sc.setTime(6, LocalTime.of(11, 0), LocalTime.of(12, 30));
        TestLog.check("IT-4", "Вільні приміщення Сб 11:00–12:30", 7, sc.getFreeRooms().size());
        sc.selectRoom(4);
        int id = sc.saveEntry().getId();
        TestLog.check("IT-5", "Запис збережено в таблиці schedule_entry", 1,
                db.queryInt("SELECT COUNT(*) FROM schedule_entry WHERE entry_id = ?", id));

        TestLog.header("Кластер LessonController + LessonRepository");
        LessonController lc = new LessonController(new LessonRepository(db), sc.getSchedule());
        TestLog.check("IT-6", "Заняття на дату без розкладу (неділя)", "ScheduleException",
                TestLog.run(() -> lc.openLesson(3, LocalDate.of(2026, 11, 8))));
        TestLog.check("IT-7", "Повторна реєстрація заняття 06.11.2026", "ScheduleException",
                TestLog.run(() -> lc.openLesson(3, LocalDate.of(2026, 11, 6))));
        Lesson l = lc.openLesson(10, LocalDate.of(2026, 11, 6)); // заняття карате не проводилось (лікарняний)
        TestLog.check("IT-8", "Список учасників секції 10", 9, l.getMarks().size());
        lc.setTopic("Відпрацювання базової техніки");
        lc.markAttendance(l.getMarks().get(0).getParticipantId(), false);
        TestLog.check("IT-9", "closeLesson: кількість присутніх", 8, lc.closeLesson());
        TestLog.check("IT-10", "Відвідування збережено в БД", 9,
                db.queryInt("SELECT COUNT(*) FROM attendance WHERE lesson_id = ?", l.getId()));
        TestLog.summary();
        db.close();
        if (TestLog.failed() > 0) System.exit(1);
    }
}
