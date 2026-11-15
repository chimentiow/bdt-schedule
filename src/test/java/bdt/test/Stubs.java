package bdt.test;

import bdt.dao.RoomSource;
import bdt.dao.ScheduleStore;
import bdt.dao.SectionSource;
import bdt.model.Leader;
import bdt.model.Room;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.model.TimeSlot;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Заглушки, що заміщують класи доступу до БД під час тестування класу ScheduleController. */
public final class Stubs {
    private Stubs() {
    }

    static final Leader L1 = new Leader(1, "Ковальчук", "Олена", "Петрівна");
    static final Leader L2 = new Leader(2, "Мельник", "Андрій", "Васильович");
    static final Section S1 = new Section(1, "Танці", "Хореографічна", L1, 15, new BigDecimal("450"));
    static final Section S2 = new Section(2, "Гітара", "Музична", L2, 8, new BigDecimal("600"));
    static final Section S3 = new Section(3, "Сучасний танець", "Хореографічна", L1, 12, new BigDecimal("500"));
    static final Room R1 = new Room(1, "101", "Навчальний клас", 16);
    static final Room R2 = new Room(2, "201", "Музичний клас", 6);
    static final Room R3 = new Room(3, "ТЗ-1", "Танцювальний зал", 20);

    static TimeSlot slot(int day, String from, String to) {
        return new TimeSlot(day, LocalTime.parse(from), LocalTime.parse(to));
    }

    /** Заглушка типу D: повертає дані з таблиці залежно від вхідного ідентифікатора. */
    static class SectionSourceStub implements SectionSource {
        private final Map<Integer, Section> sections = new HashMap<>();
        private final Map<Integer, Integer> counts = new HashMap<>();

        SectionSourceStub() {
            sections.put(1, S1); counts.put(1, 10);
            sections.put(2, S2); counts.put(2, 7);
            sections.put(3, S3); counts.put(3, 8);
        }

        @Override public Section findSection(int id) { return sections.get(id); }
        @Override public int countActiveParticipants(int id) { return counts.getOrDefault(id, 0); }
        @Override public List<Section> findAll() { return new ArrayList<>(sections.values()); }
    }

    /** Заглушка типу C: завжди повертає фіксований набір приміщень. */
    static class RoomSourceStub implements RoomSource {
        @Override public List<Room> findAll() { return List.of(R1, R2, R3); }

        @Override
        public Room findRoom(int id) {
            return findAll().stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        }
    }

    /** Заглушка типу B/C: приймає записи замість БД, повертає згенерований ідентифікатор. */
    static class ScheduleStoreStub implements ScheduleStore {
        final List<ScheduleEntry> inserted = new ArrayList<>();
        private int nextId = 100;

        @Override
        public Schedule loadSchedule() {
            Schedule s = new Schedule();
            // у понеділок 15:00–16:30 керівник L1 веде секцію S1 у залі ТЗ-1
            s.add(new ScheduleEntry(1, S1, R3, slot(1, "15:00", "16:30")));
            // у вівторок 16:00–17:30 гітара в приміщенні 201
            s.add(new ScheduleEntry(2, S2, R2, slot(2, "16:00", "17:30")));
            return s;
        }

        @Override
        public int insert(ScheduleEntry e) {
            inserted.add(e);
            return nextId++;
        }

        @Override
        public void delete(int id) {
            inserted.removeIf(e -> e.getId() == id);
        }
    }
}
