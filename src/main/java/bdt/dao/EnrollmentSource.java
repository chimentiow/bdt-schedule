package bdt.dao;

import bdt.model.FeeQuote;

/** Джерело даних про записи учасників до секцій. */
public interface EnrollmentSource {
    /** Розрахунок оплати за записом до секції або null, якщо запису немає. */
    FeeQuote findFeeQuote(int enrollmentId);
}
