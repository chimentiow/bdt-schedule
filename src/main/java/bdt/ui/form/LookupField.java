package bdt.ui.form;

import bdt.db.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/** Поле-довідник: значення обирається зі списку записів іншої таблиці, у стовпець записується ідентифікатор. */
public class LookupField extends FormField {
    private final String sql;

    /** @param sql запит довідника: перший стовпець – ідентифікатор, другий – назва */
    public LookupField(String label, String column, String sql) {
        super(label, column, true);
        this.sql = sql;
    }

    @Override
    public JComponent createEditor() {
        return new JComboBox<LookupItem>();
    }

    @Override
    public void show(JComponent editor, Object value) {
        JComboBox<LookupItem> combo = combo(editor);
        int id = value == null ? 0 : ((Number) value).intValue();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).id() == id) {
                combo.setSelectedIndex(i);
            }
        }
    }

    @Override
    public Object read(JComponent editor) {
        LookupItem selected = (LookupItem) combo(editor).getSelectedItem();
        if (selected == null) {
            throw new IllegalArgumentException("Оберіть значення поля «" + getLabel() + "».");
        }
        return selected.id();
    }

    @Override
    public void clear(JComponent editor) {
        JComboBox<LookupItem> combo = combo(editor);
        combo.setSelectedIndex(combo.getItemCount() > 0 ? 0 : -1);
    }

    /** Перечитує список довідника з бази даних, зберігаючи обране значення. */
    @Override
    public void refresh(JComponent editor, Database db) throws SQLException {
        JComboBox<LookupItem> combo = combo(editor);
        Object selected = combo.getSelectedItem();
        combo.removeAllItems();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                combo.addItem(new LookupItem(rs.getInt(1), rs.getString(2)));
            }
        }
        if (selected != null) {
            combo.setSelectedItem(selected);
        }
    }

    @SuppressWarnings("unchecked")
    private static JComboBox<LookupItem> combo(JComponent editor) {
        return (JComboBox<LookupItem>) editor;
    }
}
