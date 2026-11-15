package bdt.test.junit;

import static bdt.test.junit.TestData.slot;
import static org.junit.Assert.assertEquals;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 6. Граничні значення перетину нового заняття з наявним заняттям керівника
 * (понеділок 15:00–16:30): межі – початок і кінець наявного заняття.
 */
@RunWith(Parameterized.class)
public class OverlapBoundaryTest {
    private final String start;
    private final String end;
    private final boolean overlapExpected;

    public OverlapBoundaryTest(String name, String start, String end, boolean overlapExpected) {
        this.start = start;
        this.end = end;
        this.overlapExpected = overlapExpected;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> values() {
        return Arrays.asList(new Object[][]{
            // межа 6: нове заняття закінчується біля початку наявного (15:00)
            {"bv16 нове 13:30–14:59 (кінець перед 15:00)", "13:30", "14:59", false},
            {"bv17 нове 13:30–15:00 (кінець о 15:00)",     "13:30", "15:00", false},
            {"bv18 нове 13:30–15:01 (кінець після 15:00)", "13:30", "15:01", true},
            // межа 7: нове заняття починається біля кінця наявного (16:30)
            {"bv19 нове 16:29–18:00 (початок перед 16:30)", "16:29", "18:00", true},
            {"bv20 нове 16:30–18:00 (початок о 16:30)",     "16:30", "18:00", false},
            {"bv21 нове 16:31–18:00 (початок після 16:30)", "16:31", "18:00", false},
        });
    }

    /** Перетин на рівні класу TimeSlot. */
    @Test
    public void overlaps() {
        boolean actual = slot(TestData.MONDAY, "15:00", "16:30").overlaps(slot(TestData.MONDAY, start, end));
        assertEquals(overlapExpected, actual);
    }

    /** Те саме через контролер: перетин означає відмову «керівник зайнятий». */
    @Test
    public void leaderConflict() {
        ScheduleController controller = new ScheduleController(new TestData.MemorySections(),
                new TestData.MemoryRooms(), new TestData.MemoryScheduleStore());
        controller.newEntry();
        controller.selectSection(TestData.MODERN_DANCE.getId());
        boolean refused;
        try {
            controller.setTime(TestData.MONDAY, TestData.time(start), TestData.time(end));
            refused = false;
        } catch (ScheduleException ex) {
            refused = ex.getMessage().contains("зайнятий");
        }
        assertEquals(overlapExpected, refused);
    }
}
