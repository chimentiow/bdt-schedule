package bdt.test.junit;

import bdt.db.Database;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * База даних H2 в оперативній пам’яті для тестів обмежень цілісності: створюється зі скриптів
 * schema.sql і data.sql, тому містить ту саму структуру й тестові дані, що й робоча база.
 */
final class TestDatabase {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final Database db;

    TestDatabase() {
        db = new Database("jdbc:h2:mem:junit" + COUNTER.incrementAndGet() + ";DB_CLOSE_DELAY=-1");
        try {
            db.runScript("/schema.sql");
            db.runScript("/data.sql");
        } catch (Exception e) {
            throw new IllegalStateException("Не вдалося створити тестову базу даних", e);
        }
    }

    Database database() {
        return db;
    }

    /** Результат команди: прийнято або назва обмеження, через яке СУБД відхилила команду. */
    static final String ACCEPTED = "прийнято";
    static final String CHECK = "відхилено: CHECK";
    static final String UNIQUE = "відхилено: UNIQUE";
    static final String FOREIGN_KEY = "відхилено: FOREIGN KEY";
    static final String NOT_NULL = "відхилено: NOT NULL";
    static final String TOO_LONG = "відхилено: значення не вміщується у стовпець";

    /** Виконує команду SQL і повертає, чи прийняла її СУБД, а якщо ні – яке обмеження спрацювало. */
    String execute(String sql) {
        try (Statement st = db.getConnection().createStatement()) {
            st.executeUpdate(sql);
            return ACCEPTED;
        } catch (SQLException rejected) {
            switch (rejected.getErrorCode()) {   // коди помилок H2
                case 23513: return CHECK;
                case 23505: return UNIQUE;
                case 23506: return FOREIGN_KEY;
                case 23502: return NOT_NULL;
                case 22001: return TOO_LONG;
                default: return "помилка " + rejected.getErrorCode() + ": " + rejected.getMessage();
            }
        }
    }

    int count(String sql, Object... params) {
        try {
            return db.queryInt(sql, params);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    void close() {
        db.close();
    }

    // ---- готові команди INSERT із одним змінним значенням ----

    static String insertSection(String name, String direction, int ageMin, int ageMax, int maxParticipants,
                                String monthlyFee) {
        return "INSERT INTO section (name, direction, leader_id, age_min, age_max, max_participants, monthly_fee) "
                + "VALUES ('" + name + "', '" + direction + "', 1, " + ageMin + ", " + ageMax + ", "
                + maxParticipants + ", " + monthlyFee + ")";
    }

    static String insertRoom(String number, int capacity) {
        return "INSERT INTO room (room_number, room_type, capacity) VALUES ('" + number + "', 'Клас', "
                + capacity + ")";
    }

    static String insertParticipant(String lastName, String benefit) {
        return "INSERT INTO participant (last_name, first_name, birth_date, parent_name, parent_phone, benefit) "
                + "VALUES ('" + lastName + "', 'Тест', DATE '2015-05-05', 'Тест Тест', '+380000000000', '"
                + benefit + "')";
    }

    static String insertEntry(int dayOfWeek, String start, String end) {
        return "INSERT INTO schedule_entry (section_id, room_id, day_of_week, start_time, end_time) "
                + "VALUES (1, 1, " + dayOfWeek + ", TIME '" + start + "', TIME '" + end + "')";
    }

    /** Оплата за запис до секції № 1 за місяць, якого немає в тестових даних. */
    static String insertPayment(String period, String amount, String method) {
        return "INSERT INTO payment (enrollment_id, pay_date, period, amount, method) "
                + "VALUES (1, DATE '2027-01-10', '" + period + "', " + amount + ", '" + method + "')";
    }

    static String repeat(char c, int length) {
        return String.valueOf(c).repeat(length);
    }
}
