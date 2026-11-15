package bdt.test.junit;

import bdt.dao.LessonStore;
import bdt.dao.RoomSource;
import bdt.dao.ScheduleStore;
import bdt.dao.SectionSource;
import bdt.model.Leader;
import bdt.model.Lesson;
import bdt.model.LessonSummary;
import bdt.model.Room;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.model.TimeSlot;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Спільні тестові дані та заглушки сховищ для автоматизованих тестів JUnit.
 * Заглушки реалізують інтерфейси шару доступу до даних, тому контролери тестуються без бази даних.
 */
final class TestData {
    /** «Сьогодні» для тестів: четвер 12.11.2026. */
    static final LocalDate TODAY = LocalDate.of(2026, 11, 12);

    static final int MONDAY = 1;
    static final int TUESDAY = 2;

    static final Leader BUSY_LEADER = new Leader(1, "Ковальчук", "Олена", "Петрівна");
    static final Leader FREE_LEADER = new Leader(2, "Мельник", "Андрій", "Васильович");
    static final Leader OTHER_LEADER = new Leader(3, "Шевченко", "Ірина", "Миколаївна");

    /** Секція, заняття якої вже стоїть у розкладі: понеділок 15:00–16:30, зал ТЗ-1. */
    static final Section DANCE = section(1, "Танці", "Хореографічна", BUSY_LEADER);
    /** Друга секція того самого керівника: у понеділок 15:00–16:30 він зайнятий. */
    static final Section MODERN_DANCE = section(2, "Сучасний танець", "Хореографічна", BUSY_LEADER);
    /** Секція керівника, який у понеділок вільний. */
    static final Section GUITAR = section(3, "Гітара", "Музична", FREE_LEADER);
    /** Секція, що займає майстерню 105 у понеділок 15:00–16:30. */
    static final Section ART = section(4, "Малювання", "Художня", OTHER_LEADER);

    /** Кількість активних учасників у кожній тестовій секції. */
    static final int PARTICIPANTS = 8;

    static final Room CLASS_101 = new Room(1, "101", "Навчальний клас", 16);   // вміщує, вільне
    static final Room MUSIC_201 = new Room(2, "201", "Музичний клас", 6);      // замале, вільне
    static final Room HALL = new Room(3, "ТЗ-1", "Танцювальний зал", 20);      // вміщує, зайняте
    static final Room STUDIO_105 = new Room(4, "105", "Майстерня", 6);         // замале, зайняте

    private TestData() {
    }

    static Section section(int id, String name, String direction, Leader leader) {
        return new Section(id, name, direction, leader, 15, new BigDecimal("450.00"));
    }

    static TimeSlot slot(int day, String from, String to) {
        return new TimeSlot(day, LocalTime.parse(from), LocalTime.parse(to));
    }

    static LocalTime time(String text) {
        return LocalTime.parse(text);
    }

    /** Годинник, який завжди показує дату TODAY. */
    static Clock fixedClock() {
        ZoneId zone = ZoneId.systemDefault();
        return Clock.fixed(TODAY.atTime(12, 0).atZone(zone).toInstant(), zone);
    }

    /** Розклад із двох занять у понеділок 15:00–16:30. */
    static Schedule mondaySchedule() {
        Schedule schedule = new Schedule();
        schedule.add(new ScheduleEntry(1, DANCE, HALL, slot(MONDAY, "15:00", "16:30")));
        schedule.add(new ScheduleEntry(2, ART, STUDIO_105, slot(MONDAY, "15:00", "16:30")));
        return schedule;
    }

    /** Заглушка джерела секцій: дані зберігаються в пам’яті. */
    static class MemorySections implements SectionSource {
        private final Map<Integer, Section> sections = new LinkedHashMap<>();

        MemorySections() {
            for (Section s : List.of(DANCE, MODERN_DANCE, GUITAR, ART)) {
                sections.put(s.getId(), s);
            }
        }

        @Override public Section findSection(int sectionId) { return sections.get(sectionId); }
        @Override public int countActiveParticipants(int sectionId) { return PARTICIPANTS; }
        @Override public List<Section> findAll() { return new ArrayList<>(sections.values()); }
    }

    /** Заглушка джерела приміщень. */
    static class MemoryRooms implements RoomSource {
        private final List<Room> rooms = List.of(CLASS_101, MUSIC_201, HALL, STUDIO_105);

        @Override public List<Room> findAll() { return rooms; }

        @Override
        public Room findRoom(int roomId) {
            return rooms.stream().filter(r -> r.getId() == roomId).findFirst().orElse(null);
        }
    }

    /** Заглушка сховища розкладу: запам’ятовує записи, передані на збереження. */
    static class MemoryScheduleStore implements ScheduleStore {
        private final List<ScheduleEntry> saved = new ArrayList<>();
        private int nextId = 100;

        @Override public Schedule loadSchedule() { return mondaySchedule(); }

        @Override
        public int insert(ScheduleEntry entry) {
            saved.add(entry);
            return nextId++;
        }

        @Override public void delete(int entryId) { saved.removeIf(e -> e.getId() == entryId); }

        int getSavedCount() { return saved.size(); }
    }

    /** Заглушка сховища фактичних занять. */
    static class MemoryLessonStore implements LessonStore {
        private final boolean alreadyRegistered;
        private final List<Lesson> saved = new ArrayList<>();

        /** @param alreadyRegistered що відповідати на запитання «чи зареєстроване заняття на цю дату» */
        MemoryLessonStore(boolean alreadyRegistered) {
            this.alreadyRegistered = alreadyRegistered;
        }

        @Override public boolean exists(int sectionId, LocalDate date) { return alreadyRegistered; }

        @Override
        public Map<Integer, String> findActiveParticipants(int sectionId, LocalDate date) {
            Map<Integer, String> participants = new LinkedHashMap<>();
            participants.put(11, "Бойко Марія");
            participants.put(12, "Гнатюк Остап");
            participants.put(13, "Коваль Софія");
            return participants;
        }

        @Override
        public int save(Lesson lesson) {
            saved.add(lesson);
            return 500 + saved.size();
        }

        @Override public List<LessonSummary> findHistory(int sectionId) { return List.of(); }

        int getSavedCount() { return saved.size(); }
    }
}
