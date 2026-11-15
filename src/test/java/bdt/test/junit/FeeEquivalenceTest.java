package bdt.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import bdt.controller.PaymentController;
import bdt.controller.ScheduleException;
import bdt.dao.EnrollmentSource;
import bdt.model.FeePolicy;
import bdt.model.FeeQuote;
import bdt.model.Room;
import java.math.BigDecimal;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 5. Класи еквівалентності для розрахунку оплати (пільга учасника, відсоток знижки,
 * запис до секції) та для місткості приміщення.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeEquivalenceTest {
    private static final BigDecimal FEE = new BigDecimal("450.00");

    private static BigDecimal amountFor(String benefit) {
        return FeePolicy.forBenefit(benefit).apply(FEE);
    }

    // ---- пільга учасника ----

    @Test
    public void ec15_noBenefit_paysFullFee() {
        assertEquals(new BigDecimal("450.00"), amountFor("Немає"));
        assertEquals("Без пільг", FeePolicy.forBenefit("Немає").getName());
    }

    @Test
    public void ec16_largeFamily_pays70Percent() {
        assertEquals(new BigDecimal("315.00"), amountFor("Багатодітна"));
    }

    @Test
    public void ec17_privilegedCategory_pays50Percent() {
        assertEquals(new BigDecimal("225.00"), amountFor("Пільгова"));
    }

    @Test
    public void ec18_unknownBenefit_paysFullFee() {
        assertEquals(new BigDecimal("450.00"), amountFor("Студент"));
    }

    @Test
    public void ec19_missingBenefit_paysFullFee() {
        assertEquals(new BigDecimal("450.00"), amountFor(null));
    }

    // ---- відсоток знижки ----

    @Test
    public void ec20_discountWithinRange_isApplied() {
        FeePolicy policy = new FeePolicy.DiscountPolicy("Знижка 40%", 40);
        assertEquals(new BigDecimal("270.00"), policy.apply(FEE));
    }

    @Test
    public void ec21_negativeDiscount_isRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new FeePolicy.DiscountPolicy("Націнка", -10));
        assertEquals("Знижка має бути від 0 до 100%.", ex.getMessage());
    }

    @Test
    public void ec22_discountOver100_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FeePolicy.DiscountPolicy("Понад 100%", 150));
    }

    // ---- запис учасника до секції ----

    @Test
    public void ec23_existingEnrollment_getsQuote() {
        EnrollmentSource source = enrollmentId -> new FeeQuote(FEE, "Багатодітна");
        FeeQuote quote = new PaymentController(source).calculateFee(7);
        assertEquals(new BigDecimal("450.00"), quote.getMonthlyFee());
        assertEquals("Багатодітна сім’я (−30%)", quote.getPolicyName());
        assertEquals(new BigDecimal("315.00"), quote.getAmount());
    }

    @Test
    public void ec24_missingEnrollment_isRejected() {
        EnrollmentSource source = enrollmentId -> null;
        ScheduleException ex = assertThrows(ScheduleException.class,
                () -> new PaymentController(source).calculateFee(999));
        assertEquals("Запис учасника до секції не знайдено.", ex.getMessage());
    }

    // ---- місткість приміщення ----

    @Test
    public void ec25_fewerParticipantsThanSeats_fit() {
        assertTrue(new Room(1, "101", "Навчальний клас", 16).fits(10));
    }

    @Test
    public void ec26_moreParticipantsThanSeats_doNotFit() {
        assertFalse(new Room(1, "101", "Навчальний клас", 16).fits(25));
    }
}
