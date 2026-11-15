package bdt.ui.form;

/** Поле цілого числа. */
public class IntegerField extends TextInputField {
    public IntegerField(String label, String column) {
        super(label, column, true);
    }

    @Override
    protected Object convert(String text) {
        return Integer.parseInt(text);
    }
}
