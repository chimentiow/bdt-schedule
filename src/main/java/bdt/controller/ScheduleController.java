package bdt.controller;

import bdt.dao.RoomSource;
import bdt.dao.ScheduleStore;
import bdt.dao.SectionSource;
import bdt.model.Room;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.model.TimeSlot;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Контролер прецеденту «Додавання заняття до розкладу» (шаблон Controller).
 * Кожен публічний метод відповідає системній операції з діаграми послідовностей.
 */
public class ScheduleController {
    private final SectionSource sections;
    private final RoomSource rooms;
    private final ScheduleStore store;
    private final Schedule schedule;
    private ScheduleEntry entry;
    private int participants;

    public ScheduleController(SectionSource sections, RoomSource rooms, ScheduleStore store) {
        this.sections = sections;
        this.rooms = rooms;
        this.store = store;
        this.schedule = store.loadSchedule();
    }

    /** Системна операція 1: newEntry(). Контролер створює запис (шаблон Creator). */
    public void newEntry() {
        entry = new ScheduleEntry();
        participants = 0;
    }

    /** Системна операція 2: selectSection(sectionId). */
    public Section selectSection(int sectionId) {
        requireEntry();
        Section section = sections.findSection(sectionId);
        if (section == null) {
            throw new ScheduleException("Секцію не знайдено.");
        }
        participants = sections.countActiveParticipants(sectionId);
        entry.setSection(section);
        entry.setSlot(null);
        entry.setRoom(null);
        return section;
    }

    /** Системна операція 3: setTime(day, start, end) – перевірка зайнятості керівника. */
    public void setTime(int day, LocalTime start, LocalTime end) {
        requireEntry();
        if (entry.getSection() == null) {
            throw new ScheduleException("Спочатку оберіть секцію.");
        }
        TimeSlot slot;
        try {
            slot = new TimeSlot(day, start, end);
        } catch (IllegalArgumentException ex) {
            throw new ScheduleException(ex.getMessage());
        }
        ScheduleEntry conflict = schedule.findLeaderConflict(entry.getLeaderId(), slot);
        if (conflict != null) {
            throw new ScheduleException("Керівник " + entry.getSection().getLeader().getShortName()
                    + " у цей час зайнятий: " + conflict.describe() + ".");
        }
        entry.setSlot(slot);
        entry.setRoom(null);
    }

    /** Системна операція 4: getFreeRooms() – вільні приміщення достатньої місткості. */
    public List<Room> getFreeRooms() {
        requireEntry();
        if (entry.getSlot() == null) {
            throw new ScheduleException("Спочатку вкажіть день і час заняття.");
        }
        List<Room> result = new ArrayList<>();
        for (Room room : rooms.findAll()) {
            if (room.fits(participants) && schedule.isRoomFree(room.getId(), entry.getSlot())) {
                result.add(room);
            }
        }
        return result;
    }

    /** Системна операція 5: selectRoom(roomId). */
    public void selectRoom(int roomId) {
        requireEntry();
        if (entry.getSlot() == null) {
            throw new ScheduleException("Спочатку вкажіть день і час заняття.");
        }
        Room room = rooms.findRoom(roomId);
        if (room == null) {
            throw new ScheduleException("Приміщення не знайдено.");
        }
        if (!room.fits(participants)) {
            throw new ScheduleException("Приміщення " + room.getNumber() + " розраховане лише на "
                    + room.getCapacity() + " місць, а в секції " + participants + " учасників.");
        }
        ScheduleEntry conflict = schedule.findRoomConflict(roomId, entry.getSlot());
        if (conflict != null) {
            throw new ScheduleException("Приміщення зайняте: " + conflict.describe() + ".");
        }
        entry.setRoom(room);
    }

    /** Системна операція 6: saveEntry() – збереження запису в БД і в розкладі. */
    public ScheduleEntry saveEntry() {
        requireEntry();
        if (!entry.isComplete()) {
            throw new ScheduleException("Запис розкладу заповнено не повністю.");
        }
        int id = store.insert(entry);
        entry.setId(id);
        schedule.add(entry);
        ScheduleEntry saved = entry;
        entry = null;
        return saved;
    }

    public void deleteEntry(int entryId) {
        store.delete(entryId);
        schedule.remove(entryId);
    }

    public int getParticipants() { return participants; }
    public ScheduleEntry getCurrentEntry() { return entry; }
    public Schedule getSchedule() { return schedule; }

    private void requireEntry() {
        if (entry == null) {
            throw new ScheduleException("Новий запис розкладу не відкрито.");
        }
    }
}
