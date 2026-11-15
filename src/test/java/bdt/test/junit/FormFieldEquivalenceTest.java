package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import bdt.ui.form.DateField;
import bdt.ui.form.DecimalField;
import bdt.ui.form.IntegerField;
import bdt.ui.form.StringField;
import java.math.BigDecimal;
import java.sql.Date;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 5. Класи еквівалентності для значень, які користувач вводить у поля форм довідників:
 * ціле число, грошова сума, дата, текст.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormFieldEquivalenceTest {
    private final IntegerField capacity = new IntegerField("Місткість", "capacity");
    private final DecimalField fee = new DecimalField("Оплата за місяць, грн", "monthly_fee");
    private final DateField hireDate = new DateField("Дата прийому", "hire_date");
    private final StringField lastName = new StringField("Прізвище", "last_name", true);
    private final StringField middleName = new StringField("По батькові", "middle_name", false);

    private static void assertRejected(String expectedMessage, Runnable action) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, action::run);
        assertEquals(expectedMessage, ex.getMessage());
    }

    // ---- поле цілого числа ----

    @Test
    public void ec36_digits_areParsedAsInteger() {
        assertEquals(12, capacity.parse("12"));
    }

    @Test
    public void ec37_digitsWithSpacesAround_areParsed() {
        assertEquals(12, capacity.parse("  12 "));
    }

    @Test
    public void ec38_negativeNumber_isParsedAsInteger() {
        assertEquals(-5, capacity.parse("-5"));
    }

    @Test
    public void ec39_fractionalNumber_isRejected() {
        assertRejected("Некоректне значення поля «Місткість»: 12.5", () -> capacity.parse("12.5"));
    }

    @Test
    public void ec40_letters_areRejected() {
        assertRejected("Некоректне значення поля «Місткість»: дванадцять", () -> capacity.parse("дванадцять"));
    }

    @Test
    public void ec41_specialSymbols_areRejected() {
        assertRejected("Некоректне значення поля «Місткість»: #@+", () -> capacity.parse("#@+"));
    }

    @Test
    public void ec42_emptyRequiredField_isRejected() {
        assertRejected("Заповніть поле «Місткість».", () -> capacity.parse(""));
    }

    // ---- поле грошової суми ----

    @Test
    public void ec43_wholeAmount_isParsed() {
        assertEquals(new BigDecimal("450"), fee.parse("450"));
    }

    @Test
    public void ec44_amountWithDot_isParsed() {
        assertEquals(new BigDecimal("450.50"), fee.parse("450.50"));
    }

    @Test
    public void ec45_amountWithComma_isParsed() {
        assertEquals(new BigDecimal("450.50"), fee.parse("450,50"));
    }

    @Test
    public void ec46_amountInWords_isRejected() {
        assertRejected("Некоректне значення поля «Оплата за місяць, грн»: сто", () -> fee.parse("сто"));
    }

    @Test
    public void ec47_amountWithCurrencySign_isRejected() {
        assertRejected("Некоректне значення поля «Оплата за місяць, грн»: 450 грн", () -> fee.parse("450 грн"));
    }

    // ---- поле дати ----

    @Test
    public void ec48_isoDate_isParsed() {
        assertEquals(Date.valueOf("2026-09-01"), hireDate.parse("2026-09-01"));
    }

    @Test
    public void ec49_dateInOtherFormat_isRejected() {
        assertRejected("Некоректне значення поля «Дата прийому»: 01.09.2026", () -> hireDate.parse("01.09.2026"));
    }

    @Test
    public void ec50_nonexistentDate_isRejected() {
        assertRejected("Некоректне значення поля «Дата прийому»: 2026-02-30", () -> hireDate.parse("2026-02-30"));
    }

    @Test
    public void ec51_wordsInsteadOfDate_areRejected() {
        assertRejected("Некоректне значення поля «Дата прийому»: вчора", () -> hireDate.parse("вчора"));
    }

    // ---- текстове поле ----

    @Test
    public void ec52_text_isReturnedTrimmed() {
        assertEquals("Ковальчук", lastName.parse(" Ковальчук "));
    }

    @Test
    public void ec53_blankRequiredText_isRejected() {
        assertRejected("Заповніть поле «Прізвище».", () -> lastName.parse("   "));
    }

    @Test
    public void ec54_blankOptionalText_becomesNull() {
        assertNull(middleName.parse(""));
    }
}
