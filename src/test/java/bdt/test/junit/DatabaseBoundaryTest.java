package bdt.test.junit;

import static bdt.test.junit.TestDatabase.ACCEPTED;
import static bdt.test.junit.TestDatabase.CHECK;
import static bdt.test.junit.TestDatabase.TOO_LONG;
import static bdt.test.junit.TestDatabase.insertEntry;
import static bdt.test.junit.TestDatabase.insertPayment;
import static bdt.test.junit.TestDatabase.insertRoom;
import static bdt.test.junit.TestDatabase.insertSection;
import static bdt.test.junit.TestDatabase.repeat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Лабораторна робота 6. Граничні значення стовпців бази даних: межі обмежень CHECK, довжини рядків
 * і розрядності чисел. Кожен тест виконує одну команду INSERT у базі H2 в оперативній пам’яті.
 */
@RunWith(Parameterized.class)
public class DatabaseBoundaryTest {
    private final String sql;
    private final String expected;
    private TestDatabase db;

    public DatabaseBoundaryTest(String name, String sql, String expected) {
        this.sql = sql;
        this.expected = expected;
    }

    private static String section(int ageMin, int ageMax, int maxParticipants, String fee) {
        return insertSection("Нова секція", "Технічна", ageMin, ageMax, maxParticipants, fee);
    }

    private static String sectionNamed(int nameLength) {
        return insertSection(repeat('С', nameLength), "Технічна", 8, 14, 12, "400.00");
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> values() {
        return Arrays.asList(new Object[][]{
            // межа 14: найменший вік учасників секції (age_min >= 4)
            {"bv40 age_min = 3",                 section(3, 14, 12, "400.00"), CHECK},
            {"bv41 age_min = 4",                 section(4, 14, 12, "400.00"), ACCEPTED},
            {"bv42 age_min = 5",                 section(5, 14, 12, "400.00"), ACCEPTED},
            // межа 15: найбільший вік учасників секції (age_max <= 17)
            {"bv43 age_max = 16",                section(8, 16, 12, "400.00"), ACCEPTED},
            {"bv44 age_max = 17",                section(8, 17, 12, "400.00"), ACCEPTED},
            {"bv45 age_max = 18",                section(8, 18, 12, "400.00"), CHECK},
            // межа 16: age_max відносно age_min (age_min = 10)
            {"bv46 age_min = 10, age_max = 9",   section(10, 9, 12, "400.00"), CHECK},
            {"bv47 age_min = 10, age_max = 10",  section(10, 10, 12, "400.00"), ACCEPTED},
            {"bv48 age_min = 10, age_max = 11",  section(10, 11, 12, "400.00"), ACCEPTED},
            // межа 17: гранична кількість учасників (max_participants > 0)
            {"bv49 max_participants = 0",        section(8, 14, 0, "400.00"), CHECK},
            {"bv50 max_participants = 1",        section(8, 14, 1, "400.00"), ACCEPTED},
            {"bv51 max_participants = 2",        section(8, 14, 2, "400.00"), ACCEPTED},
            // межа 18: найменша оплата за місяць (monthly_fee >= 0)
            {"bv52 monthly_fee = -0.01",         section(8, 14, 12, "-0.01"), CHECK},
            {"bv53 monthly_fee = 0.00",          section(8, 14, 12, "0.00"), ACCEPTED},
            {"bv54 monthly_fee = 0.01",          section(8, 14, 12, "0.01"), ACCEPTED},
            // межа 19: найбільша оплата за місяць (DECIMAL(8,2): 999999.99)
            {"bv55 monthly_fee = 999999.98",     section(8, 14, 12, "999999.98"), ACCEPTED},
            {"bv56 monthly_fee = 999999.99",     section(8, 14, 12, "999999.99"), ACCEPTED},
            {"bv57 monthly_fee = 1000000.00",    section(8, 14, 12, "1000000.00"), TOO_LONG},
            // межа 20: довжина назви секції (VARCHAR(60))
            {"bv58 назва секції з 59 символів",  sectionNamed(59), ACCEPTED},
            {"bv59 назва секції з 60 символів",  sectionNamed(60), ACCEPTED},
            {"bv60 назва секції з 61 символу",   sectionNamed(61), TOO_LONG},
            // межа 21: місткість приміщення (capacity > 0)
            {"bv61 capacity = 0",                insertRoom("301", 0), CHECK},
            {"bv62 capacity = 1",                insertRoom("301", 1), ACCEPTED},
            {"bv63 capacity = 2",                insertRoom("301", 2), ACCEPTED},
            // межа 22: довжина номера приміщення (VARCHAR(10))
            {"bv64 номер приміщення з 9 символів",  insertRoom(repeat('7', 9), 10), ACCEPTED},
            {"bv65 номер приміщення з 10 символів", insertRoom(repeat('7', 10), 10), ACCEPTED},
            {"bv66 номер приміщення з 11 символів", insertRoom(repeat('7', 11), 10), TOO_LONG},
            // межа 23: найменший номер дня тижня в розкладі (day_of_week >= 1)
            {"bv67 day_of_week = 0",             insertEntry(0, "09:00", "10:30"), CHECK},
            {"bv68 day_of_week = 1",             insertEntry(1, "09:00", "10:30"), ACCEPTED},
            {"bv69 day_of_week = 2",             insertEntry(2, "09:00", "10:30"), ACCEPTED},
            // межа 24: найбільший номер дня тижня в розкладі (day_of_week <= 7)
            {"bv70 day_of_week = 6",             insertEntry(6, "09:00", "10:30"), ACCEPTED},
            {"bv71 day_of_week = 7",             insertEntry(7, "09:00", "10:30"), ACCEPTED},
            {"bv72 day_of_week = 8",             insertEntry(8, "09:00", "10:30"), CHECK},
            // межа 25: end_time відносно start_time (start_time = 15:00)
            {"bv73 end_time = 14:59",            insertEntry(3, "15:00", "14:59"), CHECK},
            {"bv74 end_time = 15:00",            insertEntry(3, "15:00", "15:00"), CHECK},
            {"bv75 end_time = 15:01",            insertEntry(3, "15:00", "15:01"), ACCEPTED},
            // межа 26: сума оплати (amount > 0)
            {"bv76 amount = -0.01",              insertPayment("2027-01", "-0.01", "Картка"), CHECK},
            {"bv77 amount = 0.00",               insertPayment("2027-01", "0.00", "Картка"), CHECK},
            {"bv78 amount = 0.01",               insertPayment("2027-01", "0.01", "Картка"), ACCEPTED},
        });
    }

    @Before
    public void createDatabase() {
        db = new TestDatabase();
    }

    @After
    public void closeDatabase() {
        db.close();
    }

    @Test
    public void insert() {
        assertEquals(expected, db.execute(sql));
    }
}
