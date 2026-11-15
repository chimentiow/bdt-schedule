package bdt.ui;

import bdt.db.Database;
import bdt.ui.form.FormField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Універсальна панель «таблиця + форма» для довідників (секції, керівники, учасники тощо).
 * Описується списком полів, тому один клас обслуговує всі довідники (повторне використання).
 */
public class CrudPanel extends JPanel {

    private final Database db;
    private final String tableName;
    private final String idColumn;
    private final String listSql;
    private final List<FormField> fields;
    private final Map<FormField, JComponent> editors = new LinkedHashMap<>();
    private final DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final StatusLabel status = new StatusLabel();
    private final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Integer editingId;
    private Runnable onChange = () -> { };

    public CrudPanel(Database db, String title, String tableName, String idColumn, String listSql,
                     List<FormField> fields) {
        super(new BorderLayout());
        this.db = db;
        this.tableName = tableName;
        this.idColumn = idColumn;
        this.listSql = listSql;
        this.fields = fields;

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                loadRow((Integer) model.getValueAt(row, 0));
            }
        });

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(title));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;
        int y = 0;
        for (FormField f : fields) {
            g.gridx = 0; g.gridy = y; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            form.add(new JLabel(f.getCaption()), g);
            JComponent ed = f.createEditor();
            editors.put(f, ed);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            form.add(ed, g);
            y++;
        }
        JButton bNew = new JButton("Новий");
        JButton bSave = new JButton("Зберегти");
        JButton bDel = new JButton("Видалити");
        bNew.addActionListener(e -> clearForm());
        bSave.addActionListener(e -> save());
        bDel.addActionListener(e -> delete());
        buttons.add(bNew);
        buttons.add(bSave);
        buttons.add(bDel);
        g.gridx = 0; g.gridy = y++; g.gridwidth = 2; g.fill = GridBagConstraints.HORIZONTAL;
        form.add(buttons, g);
        g.gridy = y++;
        form.add(status, g);
        g.gridy = y; g.weighty = 1;
        form.add(new JLabel("* – обов’язкові поля"), g);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), form);
        split.setResizeWeight(0.7);
        add(split, BorderLayout.CENTER);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            private boolean placed;

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (!placed && getWidth() > 0) {
                    placed = true;
                    split.setDividerLocation(0.68);
                }
            }
        });
        reload();
    }

    /** Додає на форму додаткову кнопку (наприклад, «Розрахувати суму» для оплат). */
    public void addAction(String text, Consumer<CrudPanel> action) {
        JButton b = new JButton(text);
        b.addActionListener(e -> action.accept(this));
        buttons.add(b);
    }

    public void setOnChange(Runnable onChange) { this.onChange = onChange; }

    public void reload() {
        try (PreparedStatement ps = db.getConnection().prepareStatement(listSql);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            List<String> cols = new ArrayList<>();
            for (int i = 1; i <= md.getColumnCount(); i++) cols.add(md.getColumnLabel(i));
            model.setDataVector(new Object[0][], cols.toArray());
            while (rs.next()) {
                Object[] row = new Object[cols.size()];
                for (int i = 0; i < row.length; i++) row[i] = rs.getObject(i + 1);
                model.addRow(row);
            }
            for (int i = 1; i < cols.size(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(i == 1 || i == 2 ? 210 : 90);
            }
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(0).setMaxWidth(55);
        } catch (SQLException e) {
            showError("Помилка завантаження: " + e.getMessage());
        }
        for (Map.Entry<FormField, JComponent> en : editors.entrySet()) {
            try {
                en.getKey().refresh(en.getValue(), db);
            } catch (SQLException e) {
                showError("Помилка довідника: " + e.getMessage());
            }
        }
    }

    private void loadRow(int id) {
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < fields.size(); i++) sql.append(i > 0 ? ", " : "").append(fields.get(i).getColumn());
        sql.append(" FROM ").append(tableName).append(" WHERE ").append(idColumn).append(" = ?");
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return;
                for (int i = 0; i < fields.size(); i++) show(fields.get(i), rs.getObject(i + 1));
            }
            editingId = id;
            status.showInfo("Редагування запису № " + id);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void setValue(String column, Object value) {
        for (FormField f : fields) if (f.getColumn().equals(column)) show(f, value);
    }

    private void show(FormField f, Object value) {
        f.show(editors.get(f), value);
    }

    /** Значення поля з форми, перетворене до типу стовпця. */
    public Object getValue(String column) {
        for (FormField f : fields) if (f.getColumn().equals(column)) return read(f);
        throw new IllegalArgumentException(column);
    }

    private Object read(FormField f) {
        return f.read(editors.get(f));
    }

    private void save() {
        try {
            List<Object> values = new ArrayList<>();
            for (FormField f : fields) values.add(read(f));
            StringBuilder sql = new StringBuilder();
            if (editingId == null) {
                sql.append("INSERT INTO ").append(tableName).append(" (");
                for (int i = 0; i < fields.size(); i++) sql.append(i > 0 ? ", " : "").append(fields.get(i).getColumn());
                sql.append(") VALUES (").append("?, ".repeat(fields.size() - 1)).append("?)");
            } else {
                sql.append("UPDATE ").append(tableName).append(" SET ");
                for (int i = 0; i < fields.size(); i++) {
                    sql.append(i > 0 ? ", " : "").append(fields.get(i).getColumn()).append(" = ?");
                }
                sql.append(" WHERE ").append(idColumn).append(" = ?");
            }
            try (PreparedStatement ps = db.getConnection().prepareStatement(sql.toString())) {
                for (int i = 0; i < values.size(); i++) ps.setObject(i + 1, values.get(i));
                if (editingId != null) ps.setInt(values.size() + 1, editingId);
                ps.executeUpdate();
            }
            boolean added = editingId == null;
            reload();
            clearForm();
            showOk(added ? "Запис додано." : "Зміни збережено.");
            onChange.run();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (SQLException ex) {
            showError(friendly(ex));
        }
    }

    private void delete() {
        if (editingId == null) {
            showError("Оберіть запис у таблиці.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Видалити запис № " + editingId + "?", "Підтвердження",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try (PreparedStatement ps = db.getConnection()
                .prepareStatement("DELETE FROM " + tableName + " WHERE " + idColumn + " = ?")) {
            ps.setInt(1, editingId);
            ps.executeUpdate();
            reload();
            clearForm();
            showOk("Запис видалено.");
            onChange.run();
        } catch (SQLException ex) {
            showError(friendly(ex));
        }
    }

    public void clearForm() {
        editingId = null;
        table.clearSelection();
        editors.forEach(FormField::clear);
        status.showInfo("Новий запис");
    }

    public void selectRow(int index) {
        table.setRowSelectionInterval(index, index);
        table.scrollRectToVisible(table.getCellRect(index, 0, true));
    }

    private static String friendly(SQLException ex) {
        String m = ex.getMessage();
        if (m.contains("Referential integrity")) return "Запис використовується в інших таблицях – видалення неможливе.";
        if (m.contains("Unique index") || m.contains("PRIMARY KEY")) return "Такий запис уже існує.";
        if (m.contains("Check constraint")) return "Значення не відповідає обмеженням (перевірте діапазони).";
        return "Помилка БД: " + m.split("\n")[0];
    }

    public void showError(String message) { status.showError(message); }
    public void showOk(String message) { status.showOk(message); }

    public Database getDb() { return db; }
    public int getRowCount() { return model.getRowCount(); }
}
