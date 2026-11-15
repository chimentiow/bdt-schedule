package bdt.test;

import bdt.controller.ScheduleController;
import bdt.model.Room;
import bdt.model.ScheduleEntry;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Драйвер типу D для тестування класу ScheduleController («сірий ящик»).
 * Класи доступу до БД заміщено заглушками з класу Stubs.
 */
public final class ScheduleControllerDriver {
    private ScheduleControllerDriver() {
    }

    public static void main(String[] args) {
        Stubs.ScheduleStoreStub store = new Stubs.ScheduleStoreStub();
        ScheduleController c = new ScheduleController(new Stubs.SectionSourceStub(), new Stubs.RoomSourceStub(), store);
        TestLog.header("Клас ScheduleController (заглушки SectionSourceStub, RoomSourceStub, ScheduleStoreStub)");

        TestLog.check("SC-1", "selectSection без newEntry()", "ScheduleException", TestLog.run(() -> c.selectSection(1)));
        c.newEntry();
        TestLog.check("SC-2", "selectSection(99) – неіснуюча секція", "ScheduleException",
                TestLog.run(() -> c.selectSection(99)));
        TestLog.check("SC-3", "setTime до вибору секції", "ScheduleException",
                TestLog.run(() -> c.setTime(1, LocalTime.of(9, 0), LocalTime.of(10, 0))));
        c.selectSection(3);
        TestLog.check("SC-4", "selectSection(3): кількість учасників", 8, c.getParticipants());
        TestLog.check("SC-5", "setTime Пн 16:00–17:30: керівник зайнятий (S1)", "ScheduleException",
                TestLog.run(() -> c.setTime(1, LocalTime.of(16, 0), LocalTime.of(17, 30))));
        TestLog.check("SC-6", "setTime 17:00–16:00: некоректний інтервал", "ScheduleException",
                TestLog.run(() -> c.setTime(1, LocalTime.of(17, 0), LocalTime.of(16, 0))));
        TestLog.check("SC-7", "setTime Вт 16:00–17:30: керівник вільний", "OK",
                TestLog.run(() -> c.setTime(2, LocalTime.of(16, 0), LocalTime.of(17, 30))));
        List<Room> free = c.getFreeRooms();
        TestLog.check("SC-8", "getFreeRooms: 201 зайняте, 201 мале", "[101, ТЗ-1]",
                free.stream().map(Room::getNumber).collect(Collectors.toList()));
        TestLog.check("SC-9", "selectRoom(2) – приміщення на 6 місць", "ScheduleException",
                TestLog.run(() -> c.selectRoom(2)));
        TestLog.check("SC-10", "saveEntry без приміщення", "ScheduleException", TestLog.run(c::saveEntry));
        c.selectRoom(3);
        ScheduleEntry saved = c.saveEntry();
        TestLog.check("SC-11", "saveEntry: id від заглушки сховища", 100, saved.getId());
        TestLog.check("SC-12", "Заглушка отримала 1 запис", 1, store.getInsertedCount());
        TestLog.check("SC-13", "Запис додано до розкладу в пам’яті", 3, c.getSchedule().size());
        c.newEntry();
        c.selectSection(1);
        TestLog.check("SC-14", "Повторне заняття того ж керівника Вт 17:00–18:00", "ScheduleException",
                TestLog.run(() -> c.setTime(2, LocalTime.of(17, 0), LocalTime.of(18, 0))));
        TestLog.summary();
        if (TestLog.failed() > 0) System.exit(1);
    }
}
