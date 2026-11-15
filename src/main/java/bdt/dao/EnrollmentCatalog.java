package bdt.dao;

import bdt.db.Database;
import bdt.model.FeeQuote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Каталог записів до секцій (Pure Fabrication): тариф секції та пільга учасника. */
public class EnrollmentCatalog implements EnrollmentSource {
    private final Database db;

    public EnrollmentCatalog(Database db) {
        this.db = db;
    }

    @Override
    public FeeQuote findFeeQuote(int enrollmentId) {
        String sql = "SELECT s.monthly_fee, p.benefit FROM enrollment e "
                + "JOIN section s ON s.section_id = e.section_id "
                + "JOIN participant p ON p.participant_id = e.participant_id WHERE e.enrollment_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new FeeQuote(rs.getBigDecimal(1), rs.getString(2)) : null;
            }
        } catch (SQLException e) {
            throw new DataException("Помилка читання запису до секції", e);
        }
    }
}
