package bdt.ui;

import static bdt.ui.form.FormField.choice;
import static bdt.ui.form.FormField.date;
import static bdt.ui.form.FormField.decimal;
import static bdt.ui.form.FormField.integer;
import static bdt.ui.form.FormField.lookup;
import static bdt.ui.form.FormField.optText;
import static bdt.ui.form.FormField.text;

import java.util.List;

/** Описи довідників програми: які таблиці редагуються на вкладках головного вікна і з якими полями. */
final class CrudSpecs {
    private static final String LEADERS = "SELECT leader_id, last_name || ' ' || first_name FROM leader ORDER BY 2";
    private static final String SECTIONS = "SELECT section_id, name FROM section ORDER BY 2";
    private static final String PARTICIPANTS =
            "SELECT participant_id, last_name || ' ' || first_name FROM participant ORDER BY 2";
    private static final String ENROLLMENTS = "SELECT e.enrollment_id, p.last_name || ' ' || p.first_name || ' – ' "
            + "|| s.name FROM enrollment e JOIN participant p ON p.participant_id = e.participant_id "
            + "JOIN section s ON s.section_id = e.section_id WHERE e.status = 'Активний' ORDER BY 2";
    private static final String EVENTS = "SELECT event_id, title FROM event ORDER BY event_date";

    private CrudSpecs() {
    }

    static CrudSpec sections() {
        String list = "SELECT s.section_id AS \"ID\", s.name AS \"Назва\", s.direction AS \"Напрям\", "
                + "l.last_name || ' ' || l.first_name AS \"Керівник\", s.age_min || '–' || s.age_max AS \"Вік\", "
                + "(SELECT COUNT(*) FROM enrollment e WHERE e.section_id = s.section_id AND e.status = 'Активний') "
                + "AS \"Учасн.\", s.max_participants AS \"Макс.\", s.monthly_fee AS \"Оплата, грн\" "
                + "FROM section s JOIN leader l ON l.leader_id = s.leader_id ORDER BY s.name";
        return new CrudSpec("Секція", "section", "section_id", list, List.of(
                text("Назва", "name"),
                choice("Напрям", "direction", "Хореографічна", "Музична", "Художня", "Технічна", "Спортивна"),
                lookup("Керівник", "leader_id", LEADERS),
                integer("Вік від", "age_min"),
                integer("Вік до", "age_max"),
                integer("Макс. учасників", "max_participants"),
                decimal("Оплата за місяць, грн", "monthly_fee")));
    }

    static CrudSpec leaders() {
        String list = "SELECT leader_id AS \"ID\", last_name AS \"Прізвище\", first_name AS \"Ім’я\", "
                + "middle_name AS \"По батькові\", phone AS \"Телефон\", specialization AS \"Спеціалізація\", "
                + "hire_date AS \"Прийнятий\" FROM leader ORDER BY last_name";
        return new CrudSpec("Керівник секції", "leader", "leader_id", list, List.of(
                text("Прізвище", "last_name"), text("Ім’я", "first_name"),
                optText("По батькові", "middle_name"), text("Телефон", "phone"),
                text("Спеціалізація", "specialization"), date("Дата прийому", "hire_date")));
    }

    static CrudSpec participants() {
        String list = "SELECT participant_id AS \"ID\", last_name AS \"Прізвище\", first_name AS \"Ім’я\", "
                + "birth_date AS \"Дата народж.\", parent_name AS \"Батьки\", parent_phone AS \"Телефон\", "
                + "benefit AS \"Пільга\" FROM participant ORDER BY last_name, first_name";
        return new CrudSpec("Учасник", "participant", "participant_id", list, List.of(
                text("Прізвище", "last_name"), text("Ім’я", "first_name"),
                date("Дата народження", "birth_date"), text("Батьки (ПІБ)", "parent_name"),
                text("Телефон батьків", "parent_phone"),
                choice("Пільга", "benefit", "Немає", "Багатодітна", "Пільгова")));
    }

    static CrudSpec enrollments() {
        String list = "SELECT e.enrollment_id AS \"ID\", p.last_name || ' ' || p.first_name AS \"Учасник\", "
                + "s.name AS \"Секція\", e.enroll_date AS \"Дата запису\", e.status AS \"Статус\" "
                + "FROM enrollment e JOIN participant p ON p.participant_id = e.participant_id "
                + "JOIN section s ON s.section_id = e.section_id ORDER BY s.name, 2";
        return new CrudSpec("Запис учасника до секції", "enrollment", "enrollment_id", list, List.of(
                lookup("Учасник", "participant_id", PARTICIPANTS), lookup("Секція", "section_id", SECTIONS),
                date("Дата запису", "enroll_date"), choice("Статус", "status", "Активний", "Вибув")));
    }

    static CrudSpec rooms() {
        String list = "SELECT room_id AS \"ID\", room_number AS \"Номер\", room_type AS \"Тип\", "
                + "capacity AS \"Місткість\" FROM room ORDER BY room_number";
        return new CrudSpec("Приміщення", "room", "room_id", list, List.of(
                text("Номер", "room_number"), text("Тип", "room_type"),
                integer("Місткість", "capacity")));
    }

    static CrudSpec events() {
        String list = "SELECT event_id AS \"ID\", title AS \"Назва\", event_type AS \"Тип\", "
                + "event_date AS \"Дата\", place AS \"Місце\" FROM event ORDER BY event_date";
        return new CrudSpec("Захід", "event", "event_id", list, List.of(
                text("Назва", "title"),
                choice("Тип", "event_type", "Концерт", "Виставка", "Конкурс", "Фестиваль", "Змагання"),
                date("Дата", "event_date"), text("Місце", "place"), optText("Опис", "description")));
    }

    static CrudSpec eventSections() {
        String list = "SELECT es.event_id * 1000 + es.section_id AS \"ID\", ev.title AS \"Захід\", "
                + "s.name AS \"Секція\", es.performance AS \"Виступ / експонат\", es.result AS \"Результат\" FROM event_section es "
                + "JOIN event ev ON ev.event_id = es.event_id JOIN section s ON s.section_id = es.section_id "
                + "ORDER BY ev.event_date, s.name";
        return new CrudSpec("Участь секції у заході", "event_section",
                "event_id * 1000 + section_id", list, List.of(
                lookup("Захід", "event_id", EVENTS), lookup("Секція", "section_id", SECTIONS),
                text("Виступ / експонат", "performance"), optText("Результат", "result")));
    }

    static CrudSpec payments() {
        String list = "SELECT pm.payment_id AS \"ID\", p.last_name || ' ' || p.first_name AS \"Учасник\", "
                + "s.name AS \"Секція\", pm.period AS \"Місяць\", pm.amount AS \"Сума, грн\", "
                + "pm.pay_date AS \"Дата оплати\", pm.method AS \"Спосіб\" FROM payment pm "
                + "JOIN enrollment e ON e.enrollment_id = pm.enrollment_id "
                + "JOIN participant p ON p.participant_id = e.participant_id "
                + "JOIN section s ON s.section_id = e.section_id ORDER BY pm.pay_date DESC, pm.payment_id DESC";
        return new CrudSpec("Оплата занять", "payment", "payment_id", list, List.of(
                lookup("Учасник – секція", "enrollment_id", ENROLLMENTS),
                text("Місяць (РРРР-ММ)", "period"), decimal("Сума, грн", "amount"),
                date("Дата оплати", "pay_date"), choice("Спосіб", "method", "Готівка", "Картка")));
    }
}
