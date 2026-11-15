package bdt.test;

import static bdt.test.Stubs.*;

import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.TimeSlot;
import java.time.LocalTime;

/**
 * Драйвер типу D для тестування методу Schedule.findLeaderConflict способом базового шляху
 * («білий ящик») та класу TimeSlot способами розбиття на еквівалентності й аналізу граничних значень.
 */
public final class ScheduleBasisPathDriver {
    private ScheduleBasisPathDriver() {
    }

    public static void main(String[] args) {
        TestLog.header("Метод Schedule.findLeaderConflict – тестування базового шляху");
        TimeSlot monday = slot(1, "15:30", "17:00");

        Schedule empty = new Schedule();
        TestLog.check("BP-1", "Шлях 1: порожній розклад", "null", describe(empty.findLeaderConflict(1, monday)));

        Schedule other = new Schedule();
        other.add(new ScheduleEntry(1, S2, R2, slot(1, "15:00", "16:30")));
        TestLog.check("BP-2", "Шлях 2: заняття іншого керівника", "null", describe(other.findLeaderConflict(1, monday)));

        Schedule noOverlap = new Schedule();
        noOverlap.add(new ScheduleEntry(1, S1, R3, slot(1, "13:00", "15:30")));
        TestLog.check("BP-3", "Шлях 3: той самий керівник, без перетину (13:00–15:30)", "null",
                describe(noOverlap.findLeaderConflict(1, monday)));

        Schedule overlap = new Schedule();
        overlap.add(new ScheduleEntry(7, S1, R3, slot(1, "15:00", "16:30")));
        TestLog.check("BP-4", "Шлях 4: той самий керівник, перетин (15:00–16:30)", "entry 7",
                describe(overlap.findLeaderConflict(1, monday)));

        TestLog.header("Клас TimeSlot – класи еквівалентності та граничні значення");
        TestLog.check("TS-1", "Коректний інтервал Пн 15:00–16:30", "OK",
                TestLog.run(() -> slot(1, "15:00", "16:30")));
        TestLog.check("TS-2", "Кінець дорівнює початку 15:00–15:00", "IllegalArgumentException",
                TestLog.run(() -> slot(1, "15:00", "15:00")));
        TestLog.check("TS-3", "Початок о 08:00 (нижня межа)", "OK", TestLog.run(() -> slot(2, "08:00", "09:00")));
        TestLog.check("TS-4", "Початок о 07:59 (за межею)", "IllegalArgumentException",
                TestLog.run(() -> slot(2, "07:59", "09:00")));
        TestLog.check("TS-5", "Кінець о 20:00 (верхня межа)", "OK", TestLog.run(() -> slot(3, "18:30", "20:00")));
        TestLog.check("TS-6", "Кінець о 20:01 (за межею)", "IllegalArgumentException",
                TestLog.run(() -> slot(3, "18:30", "20:01")));
        TestLog.check("TS-7", "День тижня 0 (поза діапазоном 1..7)", "IllegalArgumentException",
                TestLog.run(() -> new TimeSlot(0, LocalTime.of(10, 0), LocalTime.of(11, 0))));
        TestLog.check("TS-8", "День тижня 7 (неділя, межа)", "OK", TestLog.run(() -> slot(7, "10:00", "11:00")));
        TestLog.check("TS-9", "Суміжні інтервали 15:00–16:30 і 16:30–18:00", "false",
                slot(1, "15:00", "16:30").overlaps(slot(1, "16:30", "18:00")));
        TestLog.check("TS-10", "Вкладений інтервал 15:00–18:00 і 16:00–17:00", "true",
                slot(1, "15:00", "18:00").overlaps(slot(1, "16:00", "17:00")));
        TestLog.check("TS-11", "Однаковий час, різні дні", "false",
                slot(1, "15:00", "16:30").overlaps(slot(2, "15:00", "16:30")));
        TestLog.summary();
        if (TestLog.failed() > 0) System.exit(1);
    }

    private static String describe(ScheduleEntry e) {
        return e == null ? "null" : "entry " + e.getId();
    }
}
