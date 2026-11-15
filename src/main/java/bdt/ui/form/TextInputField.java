package bdt.ui.form;

import javax.swing.JComponent;
import javax.swing.JTextField;

/** Поле, значення якого вводиться текстом; підкласи визначають перетворення тексту до типу стовпця. */
public abstract class TextInputField extends FormField {
    private static final int EDITOR_COLUMNS = 18;

    protected TextInputField(String label, String column, boolean required) {
        super(label, column, required);
    }

    @Override
    public JComponent createEditor() {
        JTextField editor = new JTextField(EDITOR_COLUMNS);
        if (getHint() != null) {
            editor.setToolTipText(getHint());
        }
        return editor;
    }

    @Override
    public void show(JComponent editor, Object value) {
        ((JTextField) editor).setText(value == null ? "" : value.toString());
    }

    @Override
    public Object read(JComponent editor) {
        return parse(((JTextField) editor).getText());
    }

    @Override
    public void clear(JComponent editor) {
        ((JTextField) editor).setText("");
    }

    /** Перевіряє введений текст і перетворює його до типу стовпця. */
    public Object parse(String input) {
        String text = input.trim();
        if (text.isEmpty()) {
            if (isRequired()) {
                throw new IllegalArgumentException("Заповніть поле «" + getLabel() + "».");
            }
            return null;
        }
        try {
            return convert(text);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Некоректне значення поля «" + getLabel() + "»: " + text);
        }
    }

    /** Перетворення непорожнього тексту; некоректне значення – будь-який RuntimeException. */
    protected abstract Object convert(String text);

    /** Підказка щодо формату введення або null. */
    protected String getHint() {
        return null;
    }
}
