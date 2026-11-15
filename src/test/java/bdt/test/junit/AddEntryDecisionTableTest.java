package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.model.Room;
import bdt.model.Section;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 3. Таблиця рішень прецеденту «Додавання заняття до розкладу».
 * Кожен набір параметрів – один стовпець повної таблиці рішень (8 стовпців).
 *
 * Умови: C1 – керівник секції вільний у вказаний час; C2 – місткість приміщення не менша
 * за кількість учасників секції; C3 – приміщення вільне у вказаний час.
 */
@RunWith(Parameterized.class)
public class AddEntryDecisionTableTest {
    static final String A1_LEADER_BUSY = "повідомлення «Керівник зайнятий»";
    static final String A2_ROOM_TOO_SMALL = "повідомлення «Приміщення замале»";
    static final String A3_ROOM_OCCUPIED = "повідомлення «Приміщення зайняте»";
    static final String A4_SAVED = "запис збережено в розкладі";

    /** Рядки «Дія» повної таблиці рішень: яка дія виконується в кожному зі стовпців 0–7. */
    private static final String[] ACTIONS = {
        A1_LEADER_BUSY, A1_LEADER_BUSY, A1_LEADER_BUSY, A1_LEADER_BUSY,   // стовпці 0–3
        A2_ROOM_TOO_SMALL, A2_ROOM_TOO_SMALL,                             // стовпці 4–5
        A3_ROOM_OCCUPIED,                                                 // стовпець 6
        A4_SAVED,                                                         // стовпець 7
    };

    private final boolean leaderFree;
    private final boolean roomFits;
    private final boolean roomFree;
    private final String expectedAction;

    public AddEntryDecisionTableTest(String column, boolean c1, boolean c2, boolean c3, String action) {
        this.leaderFree = c1;
        this.roomFits = c2;
        this.roomFree = c3;
        this.expectedAction = action;
    }

    /** Повна таблиця рішень: 2^3 = 8 комбінацій умов. */
    @Parameters(name = "{0}")
    public static Collection<Object[]> columns() {
        List<Object[]> columns = new ArrayList<>();
        for (int n = 0; n < 8; n++) {
            boolean c1 = (n & 4) != 0;
            boolean c2 = (n & 2) != 0;
            boolean c3 = (n & 1) != 0;
            String name = String.format("стовпець %d: C1=%d C2=%d C3=%d", n, bit(c1), bit(c2), bit(c3));
            columns.add(new Object[]{name, c1, c2, c3, ACTIONS[n]});
        }
        return columns;
    }

    private static int bit(boolean value) {
        return value ? 1 : 0;
    }

    @Test
    public void column() {
        TestData.MemoryScheduleStore store = new TestData.MemoryScheduleStore();
        ScheduleController controller =
                new ScheduleController(new TestData.MemorySections(), new TestData.MemoryRooms(), store);

        String actualAction = addEntry(controller);

        assertEquals("дія", expectedAction, actualAction);
        boolean saved = A4_SAVED.equals(expectedAction);
        assertEquals("записів передано у сховище", saved ? 1 : 0, store.getSavedCount());
        assertEquals("записів у розкладі", saved ? 3 : 2, controller.getSchedule().size());
    }

    /** Виконує прецедент для понеділка 15:30–17:00 і повертає виконану дію. */
    private String addEntry(ScheduleController controller) {
        try {
            controller.newEntry();
            controller.selectSection(section().getId());
            controller.setTime(TestData.MONDAY, TestData.time("15:30"), TestData.time("17:00"));
            controller.selectRoom(room().getId());
            controller.saveEntry();
            return A4_SAVED;
        } catch (ScheduleException ex) {
            return classify(ex.getMessage());
        }
    }

    /** Керівник секції «Гітара» у понеділок вільний, керівник секції «Сучасний танець» веде «Танці». */
    private Section section() {
        return leaderFree ? TestData.GUITAR : TestData.MODERN_DANCE;
    }

    private Room room() {
        if (roomFits) {
            return roomFree ? TestData.CLASS_101 : TestData.HALL;
        }
        return roomFree ? TestData.MUSIC_201 : TestData.STUDIO_105;
    }

    /** Зводить текст повідомлення програми до назви дії з таблиці рішень. */
    private static String classify(String message) {
        if (message.startsWith("Керівник") && message.contains("зайнятий")) {
            return A1_LEADER_BUSY;
        }
        if (message.contains("розраховане лише на")) {
            return A2_ROOM_TOO_SMALL;
        }
        assertTrue("неочікуване повідомлення: " + message, message.startsWith("Приміщення зайняте"));
        return A3_ROOM_OCCUPIED;
    }
}
