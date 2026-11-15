package bdt.dao;

import bdt.db.Database;
import bdt.model.AttendanceMark;
import bdt.model.Lesson;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/** Репозиторій фактичних занять: заняття і відвідування зберігаються однією транзакцією. */
public class LessonRepository implements LessonStore {
    private final Database db;

    public LessonRepository(Database db) {
        this.db = db;
    }

    @Override
    public boolean exists(int sectionId, LocalDate date) {
        String sql = "SELECT COUNT(*) FROM lesson WHERE section_id = ? AND lesson_date = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DataException("Помилка перевірки заняття", e);
        }
    }

    @Override
    public Map<Integer, String> findActiveParticipants(int sectionId, LocalDate date) {
        String sql = "SELECT p.participant_id, p.last_name, p.first_name FROM enrollment e "
                + "JOIN participant p ON p.participant_id = e.participant_id "
                + "WHERE e.section_id = ? AND e.status = 'Активний' AND e.enroll_date <= ? "
                + "ORDER BY p.last_name, p.first_name";
        Map<Integer, String> result = new LinkedHashMap<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getInt(1), rs.getString(2) + " " + rs.getString(3));
                }
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання учасників", e);
        }
        return result;
    }

    @Override
    public int save(Lesson lesson) {
        String insLesson = "INSERT INTO lesson (section_id, entry_id, room_id, leader_id, lesson_date, "
                + "start_time, end_time, topic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String insMark = "INSERT INTO attendance (lesson_id, participant_id, present) VALUES (?, ?, ?)";
        try {
            Connection c = db.getConnection();
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(insLesson, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pm = c.prepareStatement(insMark)) {
                var e = lesson.getEntry();
                ps.setInt(1, e.getSection().getId());
                ps.setInt(2, e.getId());
                ps.setInt(3, e.getRoom().getId());
                ps.setInt(4, e.getLeaderId());
                ps.setDate(5, Date.valueOf(lesson.getDate()));
                ps.setTime(6, Time.valueOf(e.getSlot().getStart()));
                ps.setTime(7, Time.valueOf(e.getSlot().getEnd()));
                ps.setString(8, lesson.getTopic());
                ps.executeUpdate();
                int id;
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    id = keys.getInt(1);
                }
                for (AttendanceMark m : lesson.getMarks()) {
                    pm.setInt(1, id);
                    pm.setInt(2, m.getParticipantId());
                    pm.setBoolean(3, m.isPresent());
                    pm.addBatch();
                }
                pm.executeBatch();
                c.commit();
                return id;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new DataException("Не вдалося зберегти заняття", ex);
        }
    }
}
