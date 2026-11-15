package bdt.test.junit;

import static bdt.test.junit.TestDatabase.ACCEPTED;
import static bdt.test.junit.TestDatabase.CHECK;
import static bdt.test.junit.TestDatabase.FOREIGN_KEY;
import static bdt.test.junit.TestDatabase.NOT_NULL;
import static bdt.test.junit.TestDatabase.UNIQUE;
import static bdt.test.junit.TestDatabase.insertEntry;
import static bdt.test.junit.TestDatabase.insertParticipant;
import static bdt.test.junit.TestDatabase.insertPayment;
import static bdt.test.junit.TestDatabase.insertRoom;
import static bdt.test.junit.TestDatabase.insertSection;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Лабораторна робота 5. Класи еквівалентності для значень стовпців бази даних: перевіряється, що
 * обмеження CHECK, UNIQUE, NOT NULL і зовнішні ключі приймають допустимі значення та відхиляють недопустимі.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseConstraintsEquivalenceTest {
    private TestDatabase db;

    @Before
    public void createDatabase() {
        db = new TestDatabase();
    }

    @After
    public void closeDatabase() {
        db.close();
    }

    // ---- таблиця section ----

    @Test
    public void ec55_sectionWithValidValues_isAccepted() {
        assertEquals(ACCEPTED, db.execute(insertSection("Нова секція", "Технічна", 8, 14, 12, "400.00")));
    }

    @Test
    public void ec56_directionOutsideList_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Кулінарна", 8, 14, 12, "400.00")));
    }

    @Test
    public void ec57_minimumAgeBelow4_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Технічна", 2, 14, 12, "400.00")));
    }

    @Test
    public void ec58_maximumAgeAbove17_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Технічна", 8, 20, 12, "400.00")));
    }

    @Test
    public void ec59_maximumAgeBelowMinimumAge_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Технічна", 12, 9, 12, "400.00")));
    }

    @Test
    public void ec60_nonPositiveParticipantLimit_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Технічна", 8, 14, -3, "400.00")));
    }

    @Test
    public void ec61_negativeMonthlyFee_isRejected() {
        assertEquals(CHECK, db.execute(insertSection("Нова секція", "Технічна", 8, 14, 12, "-50.00")));
    }

    @Test
    public void ec62_duplicateSectionName_isRejected() {
        assertEquals(UNIQUE, db.execute(insertSection("Гітарна студія «Акорд»", "Музична", 8, 14, 12, "400.00")));
    }

    @Test
    public void ec63_sectionWithUnknownLeader_isRejected() {
        assertEquals(FOREIGN_KEY, db.execute("INSERT INTO section (name, direction, leader_id, age_min, age_max, "
                + "max_participants, monthly_fee) VALUES ('Нова секція', 'Технічна', 999, 8, 14, 12, 400.00)"));
    }

    // ---- таблиці room, leader, participant ----

    @Test
    public void ec64_roomWithPositiveCapacity_isAccepted() {
        assertEquals(ACCEPTED, db.execute(insertRoom("301", 10)));
    }

    @Test
    public void ec65_roomWithNegativeCapacity_isRejected() {
        assertEquals(CHECK, db.execute(insertRoom("301", -1)));
    }

    @Test
    public void ec66_leaderWithoutPhone_isRejected() {
        assertEquals(NOT_NULL, db.execute("INSERT INTO leader (last_name, first_name, specialization, hire_date) "
                + "VALUES ('Тестенко', 'Тест', 'Тренер', DATE '2026-09-01')"));
    }

    @Test
    public void ec67_participantWithKnownBenefit_isAccepted() {
        assertEquals(ACCEPTED, db.execute(insertParticipant("Тестенко", "Пільгова")));
    }

    @Test
    public void ec68_participantWithUnknownBenefit_isRejected() {
        assertEquals(CHECK, db.execute(insertParticipant("Тестенко", "VIP")));
    }

    // ---- таблиця enrollment ----

    @Test
    public void ec69_enrollmentStatusOutsideList_isRejected() {
        assertEquals(ACCEPTED, db.execute(insertParticipant("Тестенко", "Немає")));
        int participantId = db.count("SELECT MAX(participant_id) FROM participant");
        assertEquals(CHECK, db.execute("INSERT INTO enrollment (section_id, participant_id, enroll_date, status) "
                + "VALUES (1, " + participantId + ", DATE '2026-09-01', 'Пауза')"));
    }

    @Test
    public void ec70_secondEnrollmentToSameSection_isRejected() {
        assertEquals(ACCEPTED, db.execute(insertParticipant("Тестенко", "Немає")));
        int participantId = db.count("SELECT MAX(participant_id) FROM participant");
        String enroll = "INSERT INTO enrollment (section_id, participant_id, enroll_date) VALUES (1, "
                + participantId + ", DATE '2026-09-01')";
        assertEquals(ACCEPTED, db.execute(enroll));
        assertEquals(UNIQUE, db.execute(enroll));
    }

    // ---- таблиця schedule_entry ----

    @Test
    public void ec71_entryOnExistingWeekday_isAccepted() {
        assertEquals(ACCEPTED, db.execute(insertEntry(3, "09:00", "10:30")));
    }

    @Test
    public void ec72_entryOnNonexistentWeekday_isRejected() {
        assertEquals(CHECK, db.execute(insertEntry(9, "09:00", "10:30")));
    }

    @Test
    public void ec73_entryEndingBeforeStart_isRejected() {
        assertEquals(CHECK, db.execute(insertEntry(3, "12:00", "10:30")));
    }

    // ---- таблиця payment ----

    @Test
    public void ec74_paymentWithPositiveAmount_isAccepted() {
        assertEquals(ACCEPTED, db.execute(insertPayment("2027-01", "300.00", "Картка")));
    }

    @Test
    public void ec75_paymentWithNegativeAmount_isRejected() {
        assertEquals(CHECK, db.execute(insertPayment("2027-01", "-300.00", "Картка")));
    }

    @Test
    public void ec76_paymentMethodOutsideList_isRejected() {
        assertEquals(CHECK, db.execute(insertPayment("2027-01", "300.00", "Чек")));
    }

    @Test
    public void ec77_secondPaymentForSameMonth_isRejected() {
        assertEquals(ACCEPTED, db.execute(insertPayment("2027-01", "300.00", "Картка")));
        assertEquals(UNIQUE, db.execute(insertPayment("2027-01", "300.00", "Готівка")));
    }
}
