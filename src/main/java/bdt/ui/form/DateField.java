package bdt.ui.form;

import java.sql.Date;
import java.time.LocalDate;

/** Поле дати у форматі РРРР-ММ-ДД. */
public class DateField extends TextInputField {
    public DateField(String label, String column) {
        super(label, column, true);
    }

    @Override
    protected Object convert(String text) {
        return Date.valueOf(LocalDate.parse(text));
    }

    @Override
    protected String getHint() {
        return "Формат: РРРР-ММ-ДД";
    }
}
