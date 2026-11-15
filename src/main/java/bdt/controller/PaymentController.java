package bdt.controller;

import bdt.dao.EnrollmentSource;
import bdt.model.FeeQuote;

/** Контролер розрахунку оплати занять з урахуванням пільги учасника. */
public class PaymentController {
    private final EnrollmentSource enrollments;

    public PaymentController(EnrollmentSource enrollments) {
        this.enrollments = enrollments;
    }

    /** Розраховує суму оплати за записом учасника до секції. */
    public FeeQuote calculateFee(int enrollmentId) {
        FeeQuote quote = enrollments.findFeeQuote(enrollmentId);
        if (quote == null) {
            throw new ScheduleException("Запис учасника до секції не знайдено.");
        }
        return quote;
    }
}
