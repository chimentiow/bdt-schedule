package bdt.dao;

import bdt.db.Database;
import bdt.model.Leader;
import bdt.model.Section;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Каталог секцій (Pure Fabrication): читає секції та їх керівників із БД. */
public class SectionCatalog implements SectionSource {
    private static final String SELECT = "SELECT s.section_id, s.name, s.direction, s.max_participants, "
            + "s.monthly_fee, l.leader_id, l.last_name, l.first_name, l.middle_name "
            + "FROM section s JOIN leader l ON l.leader_id = s.leader_id ";
    private final Database db;

    public SectionCatalog(Database db) {
        this.db = db;
    }

    @Override
    public Section findSection(int sectionId) {
        try (PreparedStatement ps = db.getConnection().prepareStatement(SELECT + "WHERE s.section_id = ?")) {
            ps.setInt(1, sectionId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання секції", e);
        }
    }

    @Override
    public int countActiveParticipants(int sectionId) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE section_id = ? AND status = 'Активний'";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataException("Помилка підрахунку учасників", e);
        }
    }

    @Override
    public List<Section> findAll() {
        List<Section> list = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(SELECT + "ORDER BY s.name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання секцій", e);
        }
        return list;
    }

    static Section map(ResultSet rs) throws SQLException {
        Leader leader = new Leader(rs.getInt("leader_id"), rs.getString("last_name"),
                rs.getString("first_name"), rs.getString("middle_name"));
        return new Section(rs.getInt("section_id"), rs.getString("name"), rs.getString("direction"),
                leader, rs.getInt("max_participants"), rs.getBigDecimal("monthly_fee"));
    }
}
