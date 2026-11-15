package bdt.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Штучний клас (Pure Fabrication): відповідає лише за з'єднання з БД H2
 * та її первинне створення зі скриптів schema.sql і data.sql.
 */
public class Database {
    private final String url;
    private Connection connection;

    public Database(String url) {
        this.url = url;
    }

    /** База у файлі bdt_db.mv.db поруч із програмою. */
    public static Database fileDatabase() {
        return new Database("jdbc:h2:./bdt_db;AUTO_SERVER=FALSE");
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Не знайдено драйвер H2 (h2.jar)", e);
            }
            connection = DriverManager.getConnection(url, "sa", "");
        }
        return connection;
    }

    /** Створює таблиці та завантажує тестові дані, якщо база порожня. */
    public void initialize() throws SQLException, IOException {
        int tables = queryInt("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES "
                + "WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = 'SECTION'");
        if (tables > 0) {
            return;
        }
        runScript("/schema.sql");
        runScript("/data.sql");
    }

    /** Виконує запит, який повертає одне ціле число (наприклад, COUNT(*)). */
    public int queryInt(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public void runScript(String resource) throws SQLException, IOException {
        String sql = readResource(resource);
        try (Statement st = getConnection().createStatement()) {
            for (String command : sql.split(";\\s*(\\r?\\n|$)")) {
                String c = stripComments(command).trim();
                if (!c.isEmpty()) {
                    st.execute(c);
                }
            }
        }
    }

    public static String readResource(String resource) throws IOException {
        try (InputStream in = Database.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IOException("Не знайдено ресурс " + resource);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static String stripComments(String sql) {
        StringBuilder sb = new StringBuilder();
        for (String line : sql.split("\\r?\\n")) {
            if (!line.trim().startsWith("--")) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {
            // з'єднання вже закрите
        }
    }
}
