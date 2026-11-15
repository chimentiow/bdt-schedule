package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.model.Section;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 4. Тести системної операції setTime (встановлення дня і часу заняття з перевіркою
 * зайнятості керівника), побудовані методом функціональних діаграм.
 *
 * Причини: 1 – день тижня в межах 1..7; 2 – закінчення пізніше за початок; 3 – початок не раніше 08:00;
 * 4 – закінчення не пізніше 20:00; 5 – керівник має заняття, що перетинається з інтервалом.
 * Наслідки: 20 – повідомлення про день тижня; 21 – повідомлення про порядок часу; 22 – повідомлення
 * про робочий час; 23 – повідомлення про зайнятість керівника; 24 – інтервал записано в запис розкладу.
 */
@RunWith(Parameterized.class)
public class SetTimeCauseEffectTest {
    static final String E20_DAY = "День тижня має бути від 1 (Пн) до 7 (Нд).";
    static final String E21_ORDER = "Час закінчення має бути пізніше за час початку.";
    static final String E22_HOURS = "Заняття мають проходити з 08:00 до 20:00.";
    static final String E23_LEADER_BUSY = "Керівник Ковальчук О. П. у цей час зайнятий: Танці, Пн 15:00–16:30, прим. ТЗ-1.";
    static final String E24_ACCEPTED = "інтервал прийнято";

    private final int day;
    private final String start;
    private final String end;
    private final String expectedEffect;

    public SetTimeCauseEffectTest(String name, int day, String start, String end, String expectedEffect) {
        this.day = day;
        this.start = start;
        this.end = end;
        this.expectedEffect = expectedEffect;
    }

    /** Стовпці таблиці рішень, отриманої з функціональної діаграми. */
    @Parameters(name = "{0}")
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
            // назва тесту (значення причин 1–5),             день, початок, кінець, очікуваний наслідок
            {"T01 (0 1 1 1 0) день 0",                           0, "15:30", "17:00", E20_DAY},
            {"T02 (0 1 1 1 0) день 8",                           8, "15:30", "17:00", E20_DAY},
            {"T03 (1 0 1 1 0) кінець = початок",                 1, "16:30", "16:30", E21_ORDER},
            {"T04 (1 0 1 1 0) кінець раніше початку",            1, "17:00", "16:00", E21_ORDER},
            {"T05 (1 1 0 1 0) початок до 08:00",                 2, "07:30", "09:00", E22_HOURS},
            {"T06 (1 1 1 0 0) кінець після 20:00",               2, "19:00", "20:30", E22_HOURS},
            {"T07 (1 1 0 0 0) поза робочим часом з обох боків",  2, "07:00", "21:00", E22_HOURS},
            {"T08 (1 1 1 1 1) керівник зайнятий",                1, "15:30", "17:00", E23_LEADER_BUSY},
            {"T09 (1 1 1 1 0) керівник вільний, інший день",     2, "15:30", "17:00", E24_ACCEPTED},
            {"T10 (1 1 1 1 0) заняття впритул, без перетину",    1, "16:30", "18:00", E24_ACCEPTED},
            {"T11 (0 0 1 1 0) маскування: день і порядок часу",  0, "17:00", "16:00", E20_DAY},
            {"T12 (1 0 0 1 0) маскування: порядок і початок",    1, "07:00", "06:00", E21_ORDER},
        });
    }

    @Test
    public void setTime() {
        ScheduleController controller = new ScheduleController(new TestData.MemorySections(),
                new TestData.MemoryRooms(), new TestData.MemoryScheduleStore());
        // керівник секції «Сучасний танець» веде також «Танці»: понеділок 15:00–16:30
        Section section = TestData.MODERN_DANCE;
        controller.newEntry();
        controller.selectSection(section.getId());

        String actualEffect;
        try {
            controller.setTime(day, TestData.time(start), TestData.time(end));
            actualEffect = E24_ACCEPTED;
        } catch (ScheduleException ex) {
            actualEffect = ex.getMessage();
        }

        assertEquals("наслідок", expectedEffect, actualEffect);
        if (E24_ACCEPTED.equals(expectedEffect)) {
            assertNotNull("інтервал має бути записаний", controller.getCurrentEntry().getSlot());
            assertEquals(TestData.slot(day, start, end), controller.getCurrentEntry().getSlot());
        } else {
            assertNull("інтервал не має бути записаний", controller.getCurrentEntry().getSlot());
        }
    }
}
