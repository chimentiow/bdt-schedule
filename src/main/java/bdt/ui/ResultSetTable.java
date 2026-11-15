package bdt.ui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/** Перенесення результату SQL-запиту в модель таблиці: назви стовпців і всі рядки. */
final class ResultSetTable {
    private ResultSetTable() {
    }

    static void fill(DefaultTableModel model, ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        Object[] columns = new Object[meta.getColumnCount()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = meta.getColumnLabel(i + 1);
        }
        model.setDataVector(new Object[0][], columns);
        while (rs.next()) {
            Object[] row = new Object[columns.length];
            for (int i = 0; i < row.length; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }
    }
}
