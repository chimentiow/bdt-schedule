package bdt.ui.form;

/** Текстове поле. */
public class StringField extends TextInputField {
    public StringField(String label, String column, boolean required) {
        super(label, column, required);
    }

    @Override
    protected Object convert(String text) {
        return text;
    }
}
