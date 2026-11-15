package bdt.test.junit;

import static org.junit.Assert.assertEquals;

import bdt.model.TimeSlot;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 6. Граничні значення інтервалу часу заняття (конструктор класу TimeSlot):
 * для кожної межі перевіряються значення перед межею, на межі та після неї.
 */
@RunWith(Parameterized.class)
public class TimeSlotBoundaryTest {
    private static final String OK = "прийнято";
    private static final String DAY = "День тижня має бути від 1 (Пн) до 7 (Нд).";
    private static final String ORDER = "Час закінчення має бути пізніше за час початку.";
    private static final String HOURS = "Заняття мають проходити з 08:00 до 20:00.";

    private final int day;
    private final String start;
    private final String end;
    private final String expected;

    public TimeSlotBoundaryTest(String name, int day, String start, String end, String expected) {
        this.day = day;
        this.start = start;
        this.end = end;
        this.expected = expected;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> values() {
        return Arrays.asList(new Object[][]{
            // межа 1: найменший номер дня тижня (1)
            {"bv01 день 0 (перед межею 1)",                0, "10:00", "11:00", DAY},
            {"bv02 день 1 (межа)",                         1, "10:00", "11:00", OK},
            {"bv03 день 2 (після межі 1)",                 2, "10:00", "11:00", OK},
            // межа 2: найбільший номер дня тижня (7)
            {"bv04 день 6 (перед межею 7)",                6, "10:00", "11:00", OK},
            {"bv05 день 7 (межа)",                         7, "10:00", "11:00", OK},
            {"bv06 день 8 (після межі 7)",                 8, "10:00", "11:00", DAY},
            // межа 3: найраніший початок заняття (08:00)
            {"bv07 початок 07:59 (перед межею 08:00)",     1, "07:59", "09:00", HOURS},
            {"bv08 початок 08:00 (межа)",                  1, "08:00", "09:00", OK},
            {"bv09 початок 08:01 (після межі 08:00)",      1, "08:01", "09:00", OK},
            // межа 4: найпізніше закінчення заняття (20:00)
            {"bv10 кінець 19:59 (перед межею 20:00)",      1, "19:00", "19:59", OK},
            {"bv11 кінець 20:00 (межа)",                   1, "19:00", "20:00", OK},
            {"bv12 кінець 20:01 (після межі 20:00)",       1, "19:00", "20:01", HOURS},
            // межа 5: закінчення відносно початку (початок 15:00)
            {"bv13 кінець 14:59 (на хвилину раніше)",      1, "15:00", "14:59", ORDER},
            {"bv14 кінець 15:00 (дорівнює початку)",       1, "15:00", "15:00", ORDER},
            {"bv15 кінець 15:01 (на хвилину пізніше)",     1, "15:00", "15:01", OK},
        });
    }

    @Test
    public void createSlot() {
        String actual;
        try {
            new TimeSlot(day, TestData.time(start), TestData.time(end));
            actual = OK;
        } catch (IllegalArgumentException ex) {
            actual = ex.getMessage();
        }
        assertEquals(expected, actual);
    }
}
