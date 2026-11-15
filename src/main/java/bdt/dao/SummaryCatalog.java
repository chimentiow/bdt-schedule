package bdt.dao;

import bdt.db.Database;
import java.sql.SQLException;

/** Зведені показники бази даних для рядка стану головного вікна. */
public class SummaryCatalog {
    private final Database db;

    public SummaryCatalog(Database db) {
        this.db = db;
    }

    public int countSections() { return count("section"); }
    public int countParticipants() { return count("participant"); }
    public int countScheduleEntries() { return count("schedule_entry"); }
    public int countLessons() { return count("lesson"); }

    private int count(String table) {
        try {
            return db.queryInt("SELECT COUNT(*) FROM " + table);
        } catch (SQLException e) {
            throw new DataException("Помилка підрахунку записів", e);
        }
    }
}
