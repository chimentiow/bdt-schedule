package bdt.ui;

import bdt.controller.LessonController;
import bdt.controller.PaymentController;
import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.dao.DataException;
import bdt.dao.EnrollmentCatalog;
import bdt.dao.LessonRepository;
import bdt.dao.RoomCatalog;
import bdt.dao.ScheduleRepository;
import bdt.dao.SectionCatalog;
import bdt.dao.SummaryCatalog;
import bdt.db.Database;
import bdt.model.FeeQuote;
import bdt.ui.CrudPanel.Field;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/** Головне вікно ІС «Будинок дитячої творчості». */
public class MainWindow extends JFrame {
    private static final String LEADERS = "SELECT leader_id, last_name || ' ' || first_name FROM leader ORDER BY 2";
    private static final String SECTIONS = "SELECT section_id, name FROM section ORDER BY 2";
    private static final String PARTICIPANTS =
            "SELECT participant_id, last_name || ' ' || first_name FROM participant ORDER BY 2";
    private static final String ENROLLMENTS = "SELECT e.enrollment_id, p.last_name || ' ' || p.first_name || ' – ' "
            + "|| s.name FROM enrollment e JOIN participant p ON p.participant_id = e.participant_id "
            + "JOIN section s ON s.section_id = e.section_id WHERE e.status = 'Активний' ORDER BY 2";
    private static final String EVENTS = "SELECT event_id, title FROM event ORDER BY event_date";

    private final Database db;
    private final SectionCatalog sectionCatalog;
    private final SummaryCatalog summary;
    private final PaymentController paymentController;
    private final JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
    private final JLabel statusBar = new JLabel(" ");
    private final SchedulePanel schedulePanel;
    private final JournalPanel journalPanel;
    private final QueryPanel queryPanel;
    private CrudPanel paymentsPanel;

    public MainWindow(Database db) throws Exception {
        super("ІС «Будинок дитячої творчості» – облік секцій і розклад занять");
        this.db = db;
        this.sectionCatalog = new SectionCatalog(db);
        this.summary = new SummaryCatalog(db);
        this.paymentController = new PaymentController(new EnrollmentCatalog(db));
        ScheduleController scheduleController =
                new ScheduleController(sectionCatalog, new RoomCatalog(db), new ScheduleRepository(db));
        LessonController lessonController =
                new LessonController(new LessonRepository(db), scheduleController.getSchedule());

        tabs.addTab("Секції", sectionsPanel());
        tabs.addTab("Керівники", leadersPanel());
        tabs.addTab("Учасники", participantsPanel());
        tabs.addTab("Записи до секцій", enrollmentPanel());
        tabs.addTab("Приміщення", roomsPanel());
        schedulePanel = new SchedulePanel(scheduleController, this);
        tabs.addTab("Розклад", schedulePanel);
        journalPanel = new JournalPanel(lessonController, this);
        tabs.addTab("Журнал занять", journalPanel);
        tabs.addTab("Заходи", eventsPanel());
        tabs.addTab("Участь у заходах", eventSectionPanel());
        tabs.addTab("Оплата занять", paymentsPanel());
        queryPanel = new QueryPanel(db, QueryDef.loadAll());
        tabs.addTab("Запити", queryPanel);

        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 760);
        setLocationRelativeTo(null);
        refreshStatus();
    }

    private CrudPanel sectionsPanel() {
        String list = "SELECT s.section_id AS \"ID\", s.name AS \"Назва\", s.direction AS \"Напрям\", "
                + "l.last_name || ' ' || l.first_name AS \"Керівник\", s.age_min || '–' || s.age_max AS \"Вік\", "
                + "(SELECT COUNT(*) FROM enrollment e WHERE e.section_id = s.section_id AND e.status = 'Активний') "
                + "AS \"Учасн.\", s.max_participants AS \"Макс.\", s.monthly_fee AS \"Оплата, грн\" "
                + "FROM section s JOIN leader l ON l.leader_id = s.leader_id ORDER BY s.name";
        return track(new CrudPanel(db, "Секція", "section", "section_id", list, List.of(
                Field.text("Назва", "name"),
                Field.choice("Напрям", "direction", "Хореографічна|Музична|Художня|Технічна|Спортивна"),
                Field.lookup("Керівник", "leader_id", LEADERS),
                Field.integer("Вік від", "age_min"),
                Field.integer("Вік до", "age_max"),
                Field.integer("Макс. учасників", "max_participants"),
                Field.decimal("Оплата за місяць, грн", "monthly_fee"))));
    }

    private CrudPanel leadersPanel() {
        String list = "SELECT leader_id AS \"ID\", last_name AS \"Прізвище\", first_name AS \"Ім’я\", "
                + "middle_name AS \"По батькові\", phone AS \"Телефон\", specialization AS \"Спеціалізація\", "
                + "hire_date AS \"Прийнятий\" FROM leader ORDER BY last_name";
        return track(new CrudPanel(db, "Керівник секції", "leader", "leader_id", list, List.of(
                Field.text("Прізвище", "last_name"), Field.text("Ім’я", "first_name"),
                Field.optText("По батькові", "middle_name"), Field.text("Телефон", "phone"),
                Field.text("Спеціалізація", "specialization"), Field.date("Дата прийому", "hire_date"))));
    }

    private CrudPanel participantsPanel() {
        String list = "SELECT participant_id AS \"ID\", last_name AS \"Прізвище\", first_name AS \"Ім’я\", "
                + "birth_date AS \"Дата народж.\", parent_name AS \"Батьки\", parent_phone AS \"Телефон\", "
                + "benefit AS \"Пільга\" FROM participant ORDER BY last_name, first_name";
        return track(new CrudPanel(db, "Учасник", "participant", "participant_id", list, List.of(
                Field.text("Прізвище", "last_name"), Field.text("Ім’я", "first_name"),
                Field.date("Дата народження", "birth_date"), Field.text("Батьки (ПІБ)", "parent_name"),
                Field.text("Телефон батьків", "parent_phone"),
                Field.choice("Пільга", "benefit", "Немає|Багатодітна|Пільгова"))));
    }

    private CrudPanel enrollmentPanel() {
        String list = "SELECT e.enrollment_id AS \"ID\", p.last_name || ' ' || p.first_name AS \"Учасник\", "
                + "s.name AS \"Секція\", e.enroll_date AS \"Дата запису\", e.status AS \"Статус\" "
                + "FROM enrollment e JOIN participant p ON p.participant_id = e.participant_id "
                + "JOIN section s ON s.section_id = e.section_id ORDER BY s.name, 2";
        return track(new CrudPanel(db, "Запис учасника до секції", "enrollment", "enrollment_id", list, List.of(
                Field.lookup("Учасник", "participant_id", PARTICIPANTS), Field.lookup("Секція", "section_id", SECTIONS),
                Field.date("Дата запису", "enroll_date"), Field.choice("Статус", "status", "Активний|Вибув"))));
    }

    private CrudPanel roomsPanel() {
        String list = "SELECT room_id AS \"ID\", room_number AS \"Номер\", room_type AS \"Тип\", "
                + "capacity AS \"Місткість\" FROM room ORDER BY room_number";
        return track(new CrudPanel(db, "Приміщення", "room", "room_id", list, List.of(
                Field.text("Номер", "room_number"), Field.text("Тип", "room_type"),
                Field.integer("Місткість", "capacity"))));
    }

    private CrudPanel eventsPanel() {
        String list = "SELECT event_id AS \"ID\", title AS \"Назва\", event_type AS \"Тип\", "
                + "event_date AS \"Дата\", place AS \"Місце\" FROM event ORDER BY event_date";
        return track(new CrudPanel(db, "Захід", "event", "event_id", list, List.of(
                Field.text("Назва", "title"), Field.choice("Тип", "event_type", "Концерт|Виставка|Конкурс|Фестиваль|Змагання"),
                Field.date("Дата", "event_date"), Field.text("Місце", "place"), Field.optText("Опис", "description"))));
    }

    private CrudPanel eventSectionPanel() {
        String list = "SELECT es.event_id * 1000 + es.section_id AS \"ID\", ev.title AS \"Захід\", s.name AS \"Секція\", "
                + "es.performance AS \"Виступ / експонат\", es.result AS \"Результат\" FROM event_section es "
                + "JOIN event ev ON ev.event_id = es.event_id JOIN section s ON s.section_id = es.section_id "
                + "ORDER BY ev.event_date, s.name";
        return track(new CrudPanel(db, "Участь секції у заході", "event_section",
                "event_id * 1000 + section_id", list, List.of(
                Field.lookup("Захід", "event_id", EVENTS), Field.lookup("Секція", "section_id", SECTIONS),
                Field.text("Виступ / експонат", "performance"), Field.optText("Результат", "result"))));
    }

    private CrudPanel paymentsPanel() {
        String list = "SELECT pm.payment_id AS \"ID\", p.last_name || ' ' || p.first_name AS \"Учасник\", "
                + "s.name AS \"Секція\", pm.period AS \"Місяць\", pm.amount AS \"Сума, грн\", "
                + "pm.pay_date AS \"Дата оплати\", pm.method AS \"Спосіб\" FROM payment pm "
                + "JOIN enrollment e ON e.enrollment_id = pm.enrollment_id "
                + "JOIN participant p ON p.participant_id = e.participant_id "
                + "JOIN section s ON s.section_id = e.section_id ORDER BY pm.pay_date DESC, pm.payment_id DESC";
        paymentsPanel = track(new CrudPanel(db, "Оплата занять", "payment", "payment_id", list, List.of(
                Field.lookup("Учасник – секція", "enrollment_id", ENROLLMENTS),
                Field.text("Місяць (РРРР-ММ)", "period"), Field.decimal("Сума, грн", "amount"),
                Field.date("Дата оплати", "pay_date"), Field.choice("Спосіб", "method", "Готівка|Картка"))));
        paymentsPanel.addAction("Розрахувати суму", this::calculateFee);
        return paymentsPanel;
    }

    /** Розрахунок суми оплати за стратегією FeePolicy залежно від пільги учасника. */
    public void calculateFee(CrudPanel panel) {
        try {
            FeeQuote quote = paymentController.calculateFee((Integer) panel.getValue("enrollment_id"));
            panel.setValue("amount", quote.getAmount());
            panel.showOk("<html>Тариф: " + quote.getMonthlyFee() + " грн; пільга: " + quote.getPolicyName()
                    + "<br>До сплати: " + quote.getAmount() + " грн</html>");
        } catch (IllegalArgumentException | ScheduleException | DataException ex) {
            panel.showError(ex.getMessage());
        }
    }

    private CrudPanel track(CrudPanel p) {
        p.setOnChange(this::refreshStatus);
        return p;
    }

    public void refreshStatus() {
        try {
            statusBar.setText("Секцій: " + summary.countSections()
                    + "   |   Учасників: " + summary.countParticipants()
                    + "   |   Занять у розкладі на тиждень: " + summary.countScheduleEntries()
                    + "   |   Проведено занять: " + summary.countLessons());
        } catch (DataException e) {
            statusBar.setText("Помилка БД: " + e.getCause().getMessage());
        }
    }

    public Database getDatabase() { return db; }
    public SectionCatalog getSectionCatalog() { return sectionCatalog; }
    public JTabbedPane getTabs() { return tabs; }
    public SchedulePanel getSchedulePanel() { return schedulePanel; }
    public JournalPanel getJournalPanel() { return journalPanel; }
    public QueryPanel getQueryPanel() { return queryPanel; }
    public CrudPanel getPaymentsPanel() { return paymentsPanel; }
}
