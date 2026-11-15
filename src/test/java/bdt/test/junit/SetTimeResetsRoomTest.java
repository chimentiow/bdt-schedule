package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import bdt.controller.ScheduleController;
import org.junit.Test;

/**
 * Лабораторна робота 4. Додаткові перевірки наслідку 24: новий інтервал скидає раніше обране приміщення,
 * а відхилений інтервал не змінює запис.
 */
public class SetTimeResetsRoomTest {

    private ScheduleController controllerWithChosenRoom() {
        ScheduleController controller = new ScheduleController(new TestData.MemorySections(),
                new TestData.MemoryRooms(), new TestData.MemoryScheduleStore());
        controller.newEntry();
        controller.selectSection(TestData.GUITAR.getId());
        controller.setTime(TestData.TUESDAY, TestData.time("10:00"), TestData.time("11:30"));
        controller.selectRoom(TestData.CLASS_101.getId());
        return controller;
    }

    @Test
    public void acceptedTimeResetsChosenRoom() {
        ScheduleController controller = controllerWithChosenRoom();

        controller.setTime(TestData.TUESDAY, TestData.time("12:00"), TestData.time("13:30"));

        assertEquals(TestData.slot(TestData.TUESDAY, "12:00", "13:30"), controller.getCurrentEntry().getSlot());
        assertNull("приміщення треба обрати заново", controller.getCurrentEntry().getRoom());
    }

    @Test
    public void rejectedTimeKeepsPreviousSlotAndRoom() {
        ScheduleController controller = controllerWithChosenRoom();

        try {
            controller.setTime(TestData.TUESDAY, TestData.time("19:00"), TestData.time("20:30"));
        } catch (RuntimeException expected) {
            // повідомлення про робочий час перевіряє SetTimeCauseEffectTest
        }

        assertEquals(TestData.slot(TestData.TUESDAY, "10:00", "11:30"), controller.getCurrentEntry().getSlot());
        assertEquals(TestData.CLASS_101, controller.getCurrentEntry().getRoom());
    }
}
