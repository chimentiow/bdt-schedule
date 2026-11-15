package bdt.ui.form;

import javax.swing.JComboBox;
import javax.swing.JComponent;

/** Поле вибору одного значення з фіксованого списку. */
public class ChoiceField extends FormField {
    private final String[] options;

    public ChoiceField(String label, String column, String... options) {
        super(label, column, true);
        this.options = options.clone();
    }

    @Override
    public JComponent createEditor() {
        return new JComboBox<>(options);
    }

    @Override
    public void show(JComponent editor, Object value) {
        ((JComboBox<?>) editor).setSelectedItem(value);
    }

    @Override
    public Object read(JComponent editor) {
        return ((JComboBox<?>) editor).getSelectedItem();
    }

    @Override
    public void clear(JComponent editor) {
        JComboBox<?> combo = (JComboBox<?>) editor;
        combo.setSelectedIndex(combo.getItemCount() > 0 ? 0 : -1);
    }
}
