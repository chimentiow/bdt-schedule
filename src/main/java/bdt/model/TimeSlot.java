package bdt.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Часовий інтервал заняття в тижневому розкладі (клас-значення).
 * Інформаційний експерт щодо перетину двох інтервалів.
 */
public final class TimeSlot {
    public static final LocalTime OPENING = LocalTime.of(8, 0);
    public static final LocalTime CLOSING = LocalTime.of(20, 0);
    private static final String[] DAYS = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"};
    private static final String[] DAYS_FULL = {"Понеділок", "Вівторок", "Середа", "Четвер",
            "П’ятниця", "Субота", "Неділя"};
    private static final DateTimeFormatter HM = DateTimeFormatter.ofPattern("HH:mm");

    private final int day;
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(int day, LocalTime start, LocalTime end) {
        if (day < 1 || day > 7) {
            throw new IllegalArgumentException("День тижня має бути від 1 (Пн) до 7 (Нд).");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Не вказано час початку або закінчення.");
        }
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Час закінчення має бути пізніше за час початку.");
        }
        if (start.isBefore(OPENING) || end.isAfter(CLOSING)) {
            throw new IllegalArgumentException("Заняття мають проходити з 08:00 до 20:00.");
        }
        this.day = day;
        this.start = start;
        this.end = end;
    }

    /** Інтервали перетинаються, якщо вони в один день і мають спільний відрізок часу. */
    public boolean overlaps(TimeSlot other) {
        return day == other.day && start.isBefore(other.end) && other.start.isBefore(end);
    }

    public int getDay() { return day; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }

    public long getDurationMinutes() {
        return java.time.Duration.between(start, end).toMinutes();
    }

    public static String dayName(int day) { return DAYS_FULL[day - 1]; }

    /** Повні назви днів тижня від понеділка до неділі (для списків вибору). */
    public static String[] dayNames() { return DAYS_FULL.clone(); }
    public static String dayShort(int day) { return DAYS[day - 1]; }

    public String timeText() { return start.format(HM) + "–" + end.format(HM); }

    @Override
    public String toString() { return DAYS[day - 1] + " " + timeText(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeSlot t)) return false;
        return day == t.day && start.equals(t.start) && end.equals(t.end);
    }

    @Override
    public int hashCode() { return Objects.hash(day, start, end); }
}
