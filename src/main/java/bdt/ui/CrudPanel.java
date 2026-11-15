package bdt.ui;

import bdt.db.Database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Універсальна панель «таблиця + форма» для довідників (секції, керівники, учасники тощо).
 * Описується списком полів, тому один клас обслуговує всі довідники (повторне використання).
 */
public class CrudPanel extends JPanel {

    public enum Type { TEXT, INT, DECIMAL, DATE, TIME, CHOICE, LOOKUP }

    /** Опис поля форми: підпис, стовпець таблиці, тип, додаткові дані (варіанти або SQL довідника). */
    public record Field(String label, String column, Type type, String extra, boolean required) {
        public static Field text(String l, String c) { return new Field(l, c, Type.TEXT, null, true); }
        public static Field optText(String l, String c) { return new Field(l, c, Type.TEXT, null, false); }
        public static Field integer(String l, String c) { return new Field(l, c, Type.INT, null, true); }
        public static Field decimal(String l, String c) { return new Field(l, c, Type.DECIMAL, null, true); }
        public static Field date(String l, String c) { return new Field(l, c, Type.DATE, null, true); }
        public static Field choice(String l, String c, String v) { return new Field(l, c, Type.CHOICE, v, true); }
        public static Field lookup(String l, String c, String sql) { return new Field(l, c, Type.LOOKUP, sql, true); }
        public static Field optLookup(String l, String c, String sql) { return new Field(l, c, Type.LOOKUP, sql, false); }
    }

    /** Елемент списку-довідника. */
    public record Item(int id, String label) {
        @Override
        public String toString() { return label; }
    }

    private final Database db;
    private final String tableName;
    private final String idColumn;
    private final String listSql;
    private final List<Field> fields;
    private final Map<Field, JComponent> editors = new LinkedHashMap<>();
    private final DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel status = new JLabel(" ");
    private final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Integer editingId;
    private Runnable onChange = () -> { };

    public CrudPanel(Database db, String title, String tableName, String idColumn, String listSql,
                     List<Field> fields) {
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
        for (Field f : fields) {
            g.gridx = 0; g.gridy = y; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            form.add(new JLabel(f.label() + (f.required() ? ": *" : ":")), g);
            JComponent ed = createEditor(f);
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
        for (Map.Entry<Field, JComponent> en : editors.entrySet()) {
            if (en.getKey().type() == Type.LOOKUP) fillLookup(en.getKey(), castCombo(en.getValue()));
        }
    }

    private JComponent createEditor(Field f) {
        switch (f.type()) {
            case CHOICE:
                return new JComboBox<>(f.extra().split("\\|"));
            case LOOKUP:
                JComboBox<Item> cb = new JComboBox<>();
                fillLookup(f, cb);
                return cb;
            default:
                JTextField tf = new JTextField(18);
                if (f.type() == Type.DATE) tf.setToolTipText("Формат: РРРР-ММ-ДД");
                if (f.type() == Type.TIME) tf.setToolTipText("Формат: ГГ:ХХ");
                return tf;
        }
    }

    private void fillLookup(Field f, JComboBox<Item> cb) {
        Object selected = cb.getSelectedItem();
        cb.removeAllItems();
        if (!f.required()) cb.addItem(new Item(0, "—"));
        try (PreparedStatement ps = db.getConnection().prepareStatement(f.extra());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cb.addItem(new Item(rs.getInt(1), rs.getString(2)));
        } catch (SQLException e) {
            showError("Помилка довідника: " + e.getMessage());
        }
        if (selected != null) cb.setSelectedItem(selected);
    }

    private void loadRow(int id) {
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < fields.size(); i++) sql.append(i > 0 ? ", " : "").append(fields.get(i).column());
        sql.append(" FROM ").append(tableName).append(" WHERE ").append(idColumn).append(" = ?");
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return;
                for (int i = 0; i < fields.size(); i++) setValue(fields.get(i), rs.getObject(i + 1));
            }
            editingId = id;
            showInfo("Редагування запису № " + id);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void setValue(String column, Object value) {
        for (Field f : fields) if (f.column().equals(column)) setValue(f, value);
    }

    private void setValue(Field f, Object v) {
        JComponent ed = editors.get(f);
        if (f.type() == Type.LOOKUP) {
            JComboBox<Item> cb = castCombo(ed);
            int id = v == null ? 0 : ((Number) v).intValue();
            for (int i = 0; i < cb.getItemCount(); i++) {
                if (cb.getItemAt(i).id() == id) cb.setSelectedIndex(i);
            }
        } else if (f.type() == Type.CHOICE) {
            ((JComboBox<?>) ed).setSelectedItem(v);
        } else {
            String s = v == null ? "" : v.toString();
            if (f.type() == Type.TIME && s.length() >= 5) s = s.substring(0, 5);
            ((JTextField) ed).setText(s);
        }
    }

    /** Значення поля з форми, перетворене до типу стовпця. */
    public Object getValue(String column) {
        for (Field f : fields) if (f.column().equals(column)) return readValue(f);
        throw new IllegalArgumentException(column);
    }

    private Object readValue(Field f) {
        JComponent ed = editors.get(f);
        if (f.type() == Type.LOOKUP) {
            Item it = (Item) castCombo(ed).getSelectedItem();
            if (it == null || it.id() == 0) {
                if (f.required()) throw new IllegalArgumentException("Оберіть значення поля «" + f.label() + "».");
                return null;
            }
            return it.id();
        }
        if (f.type() == Type.CHOICE) return ((JComboBox<?>) ed).getSelectedItem();
        String s = ((JTextField) ed).getText().trim();
        if (s.isEmpty()) {
            if (f.required()) throw new IllegalArgumentException("Заповніть поле «" + f.label() + "».");
            return null;
        }
        try {
            return switch (f.type()) {
                case INT -> Integer.parseInt(s);
                case DECIMAL -> new BigDecimal(s.replace(',', '.'));
                case DATE -> Date.valueOf(LocalDate.parse(s));
                case TIME -> Time.valueOf(LocalTime.parse(s));
                default -> s;
            };
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Некоректне значення поля «" + f.label() + "»: " + s);
        }
    }

    private void save() {
        try {
            List<Object> values = new ArrayList<>();
            for (Field f : fields) values.add(readValue(f));
            StringBuilder sql = new StringBuilder();
            if (editingId == null) {
                sql.append("INSERT INTO ").append(tableName).append(" (");
                for (int i = 0; i < fields.size(); i++) sql.append(i > 0 ? ", " : "").append(fields.get(i).column());
                sql.append(") VALUES (").append("?, ".repeat(fields.size() - 1)).append("?)");
            } else {
                sql.append("UPDATE ").append(tableName).append(" SET ");
                for (int i = 0; i < fields.size(); i++) {
                    sql.append(i > 0 ? ", " : "").append(fields.get(i).column()).append(" = ?");
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
        for (Map.Entry<Field, JComponent> en : editors.entrySet()) {
            if (en.getValue() instanceof JTextField tf) tf.setText("");
            else ((JComboBox<?>) en.getValue()).setSelectedIndex(((JComboBox<?>) en.getValue()).getItemCount() > 0 ? 0 : -1);
        }
        showInfo("Новий запис");
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

    @SuppressWarnings("unchecked")
    private static JComboBox<Item> castCombo(JComponent c) { return (JComboBox<Item>) c; }

    public void showError(String m) { status.setForeground(new Color(180, 0, 0)); status.setText(m); }
    public void showOk(String m) { status.setForeground(new Color(0, 120, 0)); status.setText(m); }
    private void showInfo(String m) { status.setForeground(Color.DARK_GRAY); status.setText(m); }

    public Database getDb() { return db; }
    public int getRowCount() { return model.getRowCount(); }
}
