package bdt.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Рядок історії проведених занять секції. */
public final class LessonSummary {
    private static final DateTimeFormatter HM = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalDate date;
    private final LocalTime start;
    private final String roomNumber;
    private final String topic;
    private final String attendance;

    public LessonSummary(LocalDate date, LocalTime start, String roomNumber, String topic, String attendance) {
        this.date = date;
        this.start = start;
        this.roomNumber = roomNumber;
        this.topic = topic;
        this.attendance = attendance;
    }

    public LocalDate getDate() { return date; }
    public String getStartText() { return start.format(HM); }
    public String getRoomNumber() { return roomNumber; }
    public String getTopic() { return topic; }

    /** Відвідування у вигляді «присутні з усіх», наприклад «7 з 9». */
    public String getAttendance() { return attendance; }
}
