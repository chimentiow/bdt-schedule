package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import bdt.controller.LessonController;
import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.dao.SectionSource;
import bdt.model.FeePolicy;
import bdt.model.Lesson;
import bdt.model.Room;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.ui.form.IntegerField;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 6. Граничні значення місткості приміщення, відсотка знижки, дати заняття
 * та цілого числа, яке вводиться у поле форми.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CapacityAndFeeBoundaryTest {
    private static final BigDecimal FEE = new BigDecimal("450.00");

    // ---- межа 8: кількість учасників відносно місткості приміщення (16 місць) ----

    private static final Room ROOM_16 = new Room(1, "101", "Навчальний клас", 16);

    @Test
    public void bv22_participants15_fitInto16Seats() {
        assertTrue(ROOM_16.fits(15));
    }

    @Test
    public void bv23_participants16_fitInto16Seats() {
        assertTrue(ROOM_16.fits(16));
    }

    @Test
    public void bv24_participants17_doNotFitInto16Seats() {
        assertFalse(ROOM_16.fits(17));
    }

    /** Список вільних приміщень, який контролер пропонує секції із заданою кількістю учасників. */
    private static List<String> freeRoomsFor(int participants) {
        SectionSource sections = new TestData.MemorySections() {
            @Override public int countActiveParticipants(int sectionId) { return participants; }
        };
        ScheduleController controller =
                new ScheduleController(sections, new TestData.MemoryRooms(), new TestData.MemoryScheduleStore());
        controller.newEntry();
        controller.selectSection(TestData.GUITAR.getId());
        controller.setTime(TestData.TUESDAY, TestData.time("10:00"), TestData.time("11:30"));
        return controller.getFreeRooms().stream().map(Room::getNumber).collect(Collectors.toList());
    }

    // межа 9: у вівторок вільні всі приміщення; найменші (201 і 105) мають по 6 місць

    @Test
    public void bv25_section5Participants_isOfferedSmallRooms() {
        assertEquals(List.of("101", "201", "ТЗ-1", "105"), freeRoomsFor(5));
    }

    @Test
    public void bv26_section6Participants_isOfferedSmallRooms() {
        assertEquals(List.of("101", "201", "ТЗ-1", "105"), freeRoomsFor(6));
    }

    @Test
    public void bv27_section7Participants_isNotOfferedSmallRooms() {
        assertEquals(List.of("101", "ТЗ-1"), freeRoomsFor(7));
    }

    // ---- межа 10: найменший відсоток знижки (0) ----

    private static BigDecimal amountWithDiscount(int percent) {
        return new FeePolicy.DiscountPolicy("Знижка", percent).apply(FEE);
    }

    @Test
    public void bv28_discountMinus1_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> amountWithDiscount(-1));
    }

    @Test
    public void bv29_discount0_keepsFullFee() {
        assertEquals(new BigDecimal("450.00"), amountWithDiscount(0));
    }

    @Test
    public void bv30_discount1_reducesFee() {
        assertEquals(new BigDecimal("445.50"), amountWithDiscount(1));
    }

    // ---- межа 11: найбільший відсоток знижки (100) ----

    @Test
    public void bv31_discount99_leavesOnePercent() {
        assertEquals(new BigDecimal("4.50"), amountWithDiscount(99));
    }

    @Test
    public void bv32_discount100_makesLessonsFree() {
        assertEquals(new BigDecimal("0.00"), amountWithDiscount(100));
    }

    @Test
    public void bv33_discount101_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> amountWithDiscount(101));
    }

    // ---- межа 12: дата заняття відносно поточної дати (четвер 12.11.2026) ----

    /** Розклад, у якому секція «Гітара» займається в середу, четвер і п’ятницю. */
    private static LessonController controllerForWeek() {
        Schedule schedule = new Schedule();
        Section guitar = TestData.GUITAR;
        for (int day = 3; day <= 5; day++) {
            schedule.add(new ScheduleEntry(day, guitar, TestData.MUSIC_201, TestData.slot(day, "16:00", "17:30")));
        }
        return new LessonController(new TestData.MemoryLessonStore(false), schedule, TestData.fixedClock());
    }

    @Test
    public void bv34_lessonYesterday_isOpened() {
        LocalDate yesterday = TestData.TODAY.minusDays(1);
        assertEquals(yesterday, controllerForWeek().openLesson(TestData.GUITAR.getId(), yesterday).getDate());
    }

    @Test
    public void bv35_lessonToday_isOpened() {
        Lesson lesson = controllerForWeek().openLesson(TestData.GUITAR.getId(), TestData.TODAY);
        assertEquals(TestData.TODAY, lesson.getDate());
    }

    @Test
    public void bv36_lessonTomorrow_isRejected() {
        LocalDate tomorrow = TestData.TODAY.plusDays(1);
        ScheduleException ex = assertThrows(ScheduleException.class,
                () -> controllerForWeek().openLesson(TestData.GUITAR.getId(), tomorrow));
        assertEquals("Не можна зареєструвати заняття на майбутню дату.", ex.getMessage());
    }

    // ---- межа 13: найбільше ціле число, яке приймає поле форми (2 147 483 647) ----

    private final IntegerField field = new IntegerField("Макс. учасників", "max_participants");

    @Test
    public void bv37_integerBelowMaximum_isParsed() {
        assertEquals(2147483646, field.parse("2147483646"));
    }

    @Test
    public void bv38_integerMaximum_isParsed() {
        assertEquals(2147483647, field.parse("2147483647"));
    }

    @Test
    public void bv39_integerAboveMaximum_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> field.parse("2147483648"));
        assertEquals("Некоректне значення поля «Макс. учасників»: 2147483648", ex.getMessage());
    }
}
