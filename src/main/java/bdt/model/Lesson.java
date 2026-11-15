package bdt.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Фактичне заняття секції на конкретну дату з відміткою відвідування. */
public class Lesson {
    private int id;
    private final ScheduleEntry entry;
    private final LocalDate date;
    private String topic = "";
    private final List<AttendanceMark> marks = new ArrayList<>();

    public Lesson(ScheduleEntry entry, LocalDate date) {
        this.entry = entry;
        this.date = date;
    }

    /** Творець (Creator): заняття створює відмітки для своїх учасників. */
    public void addParticipant(int participantId, String name) {
        marks.add(new AttendanceMark(participantId, name, true));
    }

    public void mark(int participantId, boolean present) {
        for (AttendanceMark m : marks) {
            if (m.getParticipantId() == participantId) {
                m.setPresent(present);
                return;
            }
        }
        throw new IllegalArgumentException("Учасник не записаний до секції.");
    }

    public int countPresent() {
        int n = 0;
        for (AttendanceMark m : marks) {
            if (m.isPresent()) n++;
        }
        return n;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public ScheduleEntry getEntry() { return entry; }
    public LocalDate getDate() { return date; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public List<AttendanceMark> getMarks() { return Collections.unmodifiableList(marks); }
}
