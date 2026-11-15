package bdt.controller;

import bdt.dao.LessonStore;
import bdt.model.Lesson;
import bdt.model.LessonSummary;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Контролер прецеденту «Облік фактичного заняття». */
public class LessonController {
    /** Найбільша довжина теми заняття: розмір стовпця lesson.topic. */
    public static final int MAX_TOPIC_LENGTH = 120;

    private final LessonStore store;
    private final Schedule schedule;
    private final Clock clock;
    private Lesson lesson;

    public LessonController(LessonStore store, Schedule schedule) {
        this(store, schedule, Clock.systemDefaultZone());
    }

    /** Годинник передається параметром, щоб поточну дату можна було задати під час тестування. */
    public LessonController(LessonStore store, Schedule schedule, Clock clock) {
        this.store = store;
        this.schedule = schedule;
        this.clock = clock;
    }

    /** Системна операція 1: openLesson(sectionId, date). */
    public Lesson openLesson(int sectionId, LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now(clock))) {
            throw new ScheduleException("Не можна зареєструвати заняття на майбутню дату.");
        }
        ScheduleEntry entry = schedule.findEntry(sectionId, date.getDayOfWeek().getValue());
        if (entry == null) {
            throw new ScheduleException("За розкладом у секції немає заняття в цей день тижня.");
        }
        if (store.exists(sectionId, date)) {
            throw new ScheduleException("Заняття секції на цю дату вже зареєстроване.");
        }
        lesson = new Lesson(entry, date);
        Map<Integer, String> list = store.findActiveParticipants(sectionId, date);
        list.forEach(lesson::addParticipant);
        return lesson;
    }

    /** Системна операція 2: setTopic(topic). */
    public void setTopic(String topic) {
        requireLesson();
        if (topic == null || topic.isBlank()) {
            throw new ScheduleException("Вкажіть тему заняття.");
        }
        if (topic.trim().length() > MAX_TOPIC_LENGTH) {
            throw new ScheduleException("Тема заняття має бути не довшою за " + MAX_TOPIC_LENGTH + " символів.");
        }
        lesson.setTopic(topic.trim());
    }

    /** Системна операція 3: markAttendance(participantId, present). */
    public void markAttendance(int participantId, boolean present) {
        requireLesson();
        lesson.mark(participantId, present);
    }

    /** Системна операція 4: closeLesson() – зберігає заняття, повертає кількість присутніх. */
    public int closeLesson() {
        requireLesson();
        if (lesson.getTopic().isEmpty()) {
            throw new ScheduleException("Вкажіть тему заняття.");
        }
        lesson.setId(store.save(lesson));
        int present = lesson.countPresent();
        lesson = null;
        return present;
    }

    /** Історія проведених занять секції для журналу. */
    public List<LessonSummary> getHistory(int sectionId) {
        return store.findHistory(sectionId);
    }

    public Lesson getLesson() { return lesson; }

    private void requireLesson() {
        if (lesson == null) {
            throw new ScheduleException("Заняття не відкрито.");
        }
    }
}
