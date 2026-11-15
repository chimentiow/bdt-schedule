package bdt.ui;

import bdt.db.Database;
import bdt.ui.form.FormField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.table.TableColumnModel;

/**
 * Універсальна панель «таблиця + форма» для довідників (секції, керівники, учасники тощо).
 * Довідник задається описом CrudSpec, тому один клас обслуговує всі довідники (повторне використання).
 */
public class CrudPanel extends JPanel {
    private static final int ROW_HEIGHT = 24;
    private static final int ID_COLUMN_WIDTH = 40;
    private static final int ID_COLUMN_MAX_WIDTH = 55;
    private static final int WIDE_COLUMN_WIDTH = 210;
    private static final int COLUMN_WIDTH = 90;
    private static final double TABLE_RESIZE_WEIGHT = 0.7;
    private static final double DIVIDER_LOCATION = 0.68;

    private final Database db;
    private final CrudSpec spec;
    private final Map<FormField, JComponent> editors = new LinkedHashMap<>();
    private final DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final StatusLabel status = new StatusLabel();
    private final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Integer editingId;
    private Runnable onChange = () -> { };

    public CrudPanel(Database db, CrudSpec spec) {
        super(new BorderLayout());
        this.db = db;
        this.spec = spec;
        configureTable();
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), buildForm());
        split.setResizeWeight(TABLE_RESIZE_WEIGHT);
        add(split, BorderLayout.CENTER);
        placeDividerOnFirstResize(split);
        reload();
    }

    private void configureTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(ROW_HEIGHT);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                loadRow((Integer) model.getValueAt(row, 0));
            }
        });
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(spec.title()));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        for (FormField field : spec.fields()) {
            addFieldRow(form, gbc, row++, field);
        }
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(buildButtons(), gbc);
        gbc.gridy = row++;
        form.add(status, gbc);
        gbc.gridy = row;
        gbc.weighty = 1;
        form.add(new JLabel("* – обов’язкові поля"), gbc);
        return form;
    }

    /** Рядок форми: підпис поля ліворуч, компонент редагування праворуч. */
    private void addFieldRow(JPanel form, GridBagConstraints gbc, int row, FormField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel(field.getCaption()), gbc);
        JComponent editor = field.createEditor();
        editors.put(field, editor);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(editor, gbc);
    }

    private JPanel buildButtons() {
        addButton("Новий", this::clearForm);
        addButton("Зберегти", this::save);
        addButton("Видалити", this::delete);
        return buttons;
    }

    private void addButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        buttons.add(button);
    }

    /** Початкове положення роздільника встановлюється, коли панель уперше отримує розмір. */
    private void placeDividerOnFirstResize(JSplitPane split) {
        addComponentListener(new ComponentAdapter() {
            private boolean placed;

            @Override
            public void componentResized(ComponentEvent e) {
                if (!placed && getWidth() > 0) {
                    placed = true;
                    split.setDividerLocation(DIVIDER_LOCATION);
                }
            }
        });
    }

    /** Додає на форму додаткову кнопку (наприклад, «Розрахувати суму» для оплат). */
    public void addAction(String text, Consumer<CrudPanel> action) {
        addButton(text, () -> action.accept(this));
    }

    public void setOnChange(Runnable onChange) { this.onChange = onChange; }

    /** Перечитує з бази даних список записів і списки полів-довідників. */
    public void reload() {
        try (PreparedStatement ps = db.getConnection().prepareStatement(spec.listSql());
             ResultSet rs = ps.executeQuery()) {
            ResultSetTable.fill(model, rs);
            adjustColumnWidths();
        } catch (SQLException e) {
            showError("Помилка завантаження: " + e.getMessage());
        }
        refreshLookups();
    }

    private void adjustColumnWidths() {
        TableColumnModel columns = table.getColumnModel();
        for (int i = 1; i < columns.getColumnCount(); i++) {
            boolean wide = i == 1 || i == 2;
            columns.getColumn(i).setPreferredWidth(wide ? WIDE_COLUMN_WIDTH : COLUMN_WIDTH);
        }
        columns.getColumn(0).setPreferredWidth(ID_COLUMN_WIDTH);
        columns.getColumn(0).setMaxWidth(ID_COLUMN_MAX_WIDTH);
    }

    private void refreshLookups() {
        for (Map.Entry<FormField, JComponent> entry : editors.entrySet()) {
            try {
                entry.getKey().refresh(entry.getValue(), db);
            } catch (SQLException e) {
                showError("Помилка довідника: " + e.getMessage());
            }
        }
    }

    private void loadRow(int id) {
        try (PreparedStatement ps = db.getConnection().prepareStatement(spec.selectSql())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return;
                }
                int column = 1;
                for (FormField field : spec.fields()) {
                    show(field, rs.getObject(column++));
                }
            }
            editingId = id;
            status.showInfo("Редагування запису № " + id);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public void setValue(String column, Object value) {
        for (FormField field : spec.fields()) {
            if (field.getColumn().equals(column)) {
                show(field, value);
            }
        }
    }

    private void show(FormField field, Object value) {
        field.show(editors.get(field), value);
    }

    /** Значення поля з форми, перетворене до типу стовпця. */
    public Object getValue(String column) {
        for (FormField field : spec.fields()) {
            if (field.getColumn().equals(column)) {
                return read(field);
            }
        }
        throw new IllegalArgumentException(column);
    }

    private Object read(FormField field) {
        return field.read(editors.get(field));
    }

    private List<Object> readValues() {
        List<Object> values = new ArrayList<>();
        for (FormField field : spec.fields()) {
            values.add(read(field));
        }
        return values;
    }

    private void save() {
        try {
            List<Object> params = readValues();
            boolean adding = editingId == null;
            if (!adding) {
                params.add(editingId);
            }
            execute(adding ? spec.insertSql() : spec.updateSql(), params);
            afterChange(adding ? "Запис додано." : "Зміни збережено.");
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
        if (!confirm("Видалити запис № " + editingId + "?")) {
            return;
        }
        try {
            execute(spec.deleteSql(), List.of(editingId));
            afterChange("Запис видалено.");
        } catch (SQLException ex) {
            showError(friendly(ex));
        }
    }

    private boolean confirm(String question) {
        return JOptionPane.showConfirmDialog(this, question, "Підтвердження", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    private void execute(String sql, List<Object> params) throws SQLException {
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.executeUpdate();
        }
    }

    /** Оновлює таблицю і форму після зміни даних та сповіщає головне вікно. */
    private void afterChange(String message) {
        reload();
        clearForm();
        showOk(message);
        onChange.run();
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

    /** Перетворює повідомлення СУБД про порушення обмежень на зрозуміле користувачеві. */
    private static String friendly(SQLException ex) {
        String message = ex.getMessage();
        if (message.contains("Referential integrity")) {
            return "Запис використовується в інших таблицях – видалення неможливе.";
        }
        if (message.contains("Unique index") || message.contains("PRIMARY KEY")) {
            return "Такий запис уже існує.";
        }
        if (message.contains("Check constraint")) {
            return "Значення не відповідає обмеженням (перевірте діапазони).";
        }
        return "Помилка БД: " + message.split("\n")[0];
    }

    public void showError(String message) { status.showError(message); }
    public void showOk(String message) { status.showOk(message); }

    public Database getDb() { return db; }
    public int getRowCount() { return model.getRowCount(); }
}
