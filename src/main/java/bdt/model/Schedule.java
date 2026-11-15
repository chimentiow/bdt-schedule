package bdt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Тижневий розклад занять. Інформаційний експерт щодо зайнятості
 * керівників і приміщень, бо містить усі записи розкладу.
 */
public class Schedule {
    private final List<ScheduleEntry> entries = new ArrayList<>();

    public void add(ScheduleEntry entry) {
        entries.add(entry);
    }

    public boolean remove(int entryId) {
        return entries.removeIf(e -> e.getId() == entryId);
    }

    /** Повертає запис, з яким керівник має перетин у часі, або null, якщо керівник вільний. */
    public ScheduleEntry findLeaderConflict(int leaderId, TimeSlot slot) {
        for (ScheduleEntry e : entries) {
            if (e.getLeaderId() == leaderId && e.getSlot().overlaps(slot)) {
                return e;
            }
        }
        return null;
    }

    /** Повертає запис, який займає приміщення у вказаний час, або null. */
    public ScheduleEntry findRoomConflict(int roomId, TimeSlot slot) {
        for (ScheduleEntry e : entries) {
            if (e.getRoom().getId() == roomId && e.getSlot().overlaps(slot)) {
                return e;
            }
        }
        return null;
    }

    public boolean isLeaderFree(int leaderId, TimeSlot slot) {
        return findLeaderConflict(leaderId, slot) == null;
    }

    public boolean isRoomFree(int roomId, TimeSlot slot) {
        return findRoomConflict(roomId, slot) == null;
    }

    /** Запис розкладу секції на заданий день тижня (для обліку фактичних занять). */
    public ScheduleEntry findEntry(int sectionId, int day) {
        for (ScheduleEntry e : entries) {
            if (e.getSection().getId() == sectionId && e.getSlot().getDay() == day) {
                return e;
            }
        }
        return null;
    }

    public List<ScheduleEntry> getEntries() {
        List<ScheduleEntry> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparing((ScheduleEntry e) -> e.getSlot().getDay())
                .thenComparing(e -> e.getSlot().getStart())
                .thenComparing(e -> e.getRoom().getNumber()));
        return Collections.unmodifiableList(sorted);
    }

    public int size() {
        return entries.size();
    }
}
