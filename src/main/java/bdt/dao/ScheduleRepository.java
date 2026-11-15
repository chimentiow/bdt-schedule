package bdt.dao;

import bdt.db.Database;
import bdt.model.Room;
import bdt.model.Schedule;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.model.TimeSlot;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/** Репозиторій розкладу (Pure Fabrication): збереження записів розкладу в таблиці schedule_entry. */
public class ScheduleRepository implements ScheduleStore {
    private final Database db;

    public ScheduleRepository(Database db) {
        this.db = db;
    }

    @Override
    public Schedule loadSchedule() {
        String sql = "SELECT se.entry_id, se.day_of_week, se.start_time, se.end_time, "
                + "s.section_id, s.name, s.direction, s.max_participants, s.monthly_fee, "
                + "l.leader_id, l.last_name, l.first_name, l.middle_name, "
                + "r.room_id, r.room_number, r.room_type, r.capacity "
                + "FROM schedule_entry se JOIN section s ON s.section_id = se.section_id "
                + "JOIN leader l ON l.leader_id = s.leader_id JOIN room r ON r.room_id = se.room_id";
        Schedule schedule = new Schedule();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Section section = SectionCatalog.map(rs);
                Room room = RoomCatalog.map(rs);
                TimeSlot slot = new TimeSlot(rs.getInt("day_of_week"),
                        rs.getTime("start_time").toLocalTime(), rs.getTime("end_time").toLocalTime());
                schedule.add(new ScheduleEntry(rs.getInt("entry_id"), section, room, slot));
            }
        } catch (SQLException e) {
            throw new DataException("Помилка завантаження розкладу", e);
        }
        return schedule;
    }

    @Override
    public int insert(ScheduleEntry entry) {
        String sql = "INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entry.getSection().getId());
            ps.setInt(2, entry.getRoom().getId());
            ps.setInt(3, entry.getSlot().getDay());
            ps.setTime(4, Time.valueOf(entry.getSlot().getStart()));
            ps.setTime(5, Time.valueOf(entry.getSlot().getEnd()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataException("Не вдалося зберегти запис розкладу", e);
        }
    }

    @Override
    public void delete(int entryId) {
        try (PreparedStatement ps = db.getConnection()
                .prepareStatement("DELETE FROM schedule_entry WHERE entry_id = ?")) {
            ps.setInt(1, entryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Не вдалося видалити запис розкладу", e);
        }
    }
}
