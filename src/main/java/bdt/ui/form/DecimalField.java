package bdt.ui.form;

import java.math.BigDecimal;

/** Поле десяткового числа (грошова сума); дробову частину можна відділяти комою або крапкою. */
public class DecimalField extends TextInputField {
    public DecimalField(String label, String column) {
        super(label, column, true);
    }

    @Override
    protected Object convert(String text) {
        return new BigDecimal(text.replace(',', '.'));
    }
}
