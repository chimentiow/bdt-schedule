package bdt.test.junit;

import static bdt.test.junit.TestData.slot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import bdt.model.TimeSlot;
import java.time.LocalTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 5. Класи еквівалентності для інтервалу часу заняття (клас TimeSlot):
 * день тижня, час початку й закінчення, взаємне розташування двох інтервалів.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeSlotEquivalenceTest {
    private static final LocalTime TEN = LocalTime.of(10, 0);
    private static final LocalTime ELEVEN = LocalTime.of(11, 0);

    // ---- день тижня ----

    @Test
    public void ec01_dayWithinWeek_isAccepted() {
        assertEquals(3, new TimeSlot(3, TEN, ELEVEN).getDay());
    }

    @Test
    public void ec02_dayBelowRange_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new TimeSlot(-2, TEN, ELEVEN));
        assertEquals("День тижня має бути від 1 (Пн) до 7 (Нд).", ex.getMessage());
    }

    @Test
    public void ec03_dayAboveRange_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new TimeSlot(10, TEN, ELEVEN));
        assertEquals("День тижня має бути від 1 (Пн) до 7 (Нд).", ex.getMessage());
    }

    // ---- час початку і закінчення ----

    @Test
    public void ec04_timeWithinWorkingHours_isAccepted() {
        TimeSlot lesson = slot(1, "10:00", "11:30");
        assertEquals(90, lesson.getDurationMinutes());
    }

    @Test
    public void ec05_endBeforeStart_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> slot(1, "12:00", "11:00"));
        assertEquals("Час закінчення має бути пізніше за час початку.", ex.getMessage());
    }

    @Test
    public void ec06_startBeforeOpening_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> slot(1, "06:00", "09:00"));
        assertEquals("Заняття мають проходити з 08:00 до 20:00.", ex.getMessage());
    }

    @Test
    public void ec07_endAfterClosing_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> slot(1, "19:00", "22:00"));
        assertEquals("Заняття мають проходити з 08:00 до 20:00.", ex.getMessage());
    }

    @Test
    public void ec08_missingStart_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new TimeSlot(1, null, ELEVEN));
        assertEquals("Не вказано час початку або закінчення.", ex.getMessage());
    }

    @Test
    public void ec09_missingEnd_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new TimeSlot(1, TEN, null));
        assertEquals("Не вказано час початку або закінчення.", ex.getMessage());
    }

    // ---- взаємне розташування двох інтервалів (метод overlaps) ----

    @Test
    public void ec10_partlyOverlappingIntervals_overlap() {
        assertTrue(slot(1, "15:00", "16:30").overlaps(slot(1, "16:00", "17:30")));
    }

    @Test
    public void ec11_nestedInterval_overlaps() {
        assertTrue(slot(1, "15:00", "18:00").overlaps(slot(1, "16:00", "17:00")));
    }

    @Test
    public void ec12_identicalIntervals_overlap() {
        assertTrue(slot(1, "15:00", "16:30").overlaps(slot(1, "15:00", "16:30")));
    }

    @Test
    public void ec13_separateIntervalsSameDay_doNotOverlap() {
        assertFalse(slot(1, "09:00", "10:30").overlaps(slot(1, "15:00", "16:30")));
    }

    @Test
    public void ec14_sameTimeOnDifferentDays_doNotOverlap() {
        assertFalse(slot(1, "15:00", "16:30").overlaps(slot(2, "15:00", "16:30")));
    }
}
