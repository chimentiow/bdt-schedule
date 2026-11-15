package bdt.model;

/** Запис тижневого розкладу: секція + приміщення + часовий інтервал. */
public class ScheduleEntry {
    private int id;
    private Section section;
    private Room room;
    private TimeSlot slot;

    public ScheduleEntry() {
    }

    public ScheduleEntry(int id, Section section, Room room, TimeSlot slot) {
        this.id = id;
        this.section = section;
        this.room = room;
        this.slot = slot;
    }

    public boolean isComplete() {
        return section != null && room != null && slot != null;
    }

    public int getLeaderId() {
        return section.getLeader().getId();
    }

    public String describe() {
        return section.getName() + ", " + slot + ", прим. " + room.getNumber();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Section getSection() { return section; }
    public void setSection(Section section) { this.section = section; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public TimeSlot getSlot() { return slot; }
    public void setSlot(TimeSlot slot) { this.slot = slot; }
}
