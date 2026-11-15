package bdt.dao;

import bdt.model.Lesson;
import java.time.LocalDate;
import java.util.Map;

/** Сховище фактичних занять і відвідувань. */
public interface LessonStore {
    boolean exists(int sectionId, LocalDate date);

    /** Активні учасники секції на дату: id -> «Прізвище Ім’я». */
    Map<Integer, String> findActiveParticipants(int sectionId, LocalDate date);

    int save(Lesson lesson);
}
