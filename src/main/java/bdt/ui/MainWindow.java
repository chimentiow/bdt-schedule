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
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/** Головне вікно ІС «Будинок дитячої творчості». */
public class MainWindow extends JFrame {
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

        tabs.addTab("Секції", crudPanel(CrudSpecs.sections()));
        tabs.addTab("Керівники", crudPanel(CrudSpecs.leaders()));
        tabs.addTab("Учасники", crudPanel(CrudSpecs.participants()));
        tabs.addTab("Записи до секцій", crudPanel(CrudSpecs.enrollments()));
        tabs.addTab("Приміщення", crudPanel(CrudSpecs.rooms()));
        schedulePanel = new SchedulePanel(scheduleController, this);
        tabs.addTab("Розклад", schedulePanel);
        journalPanel = new JournalPanel(lessonController, this);
        tabs.addTab("Журнал занять", journalPanel);
        tabs.addTab("Заходи", crudPanel(CrudSpecs.events()));
        tabs.addTab("Участь у заходах", crudPanel(CrudSpecs.eventSections()));
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

    private CrudPanel crudPanel(CrudSpec spec) {
        CrudPanel panel = new CrudPanel(db, spec);
        panel.setOnChange(this::refreshStatus);
        return panel;
    }

    private CrudPanel paymentsPanel() {
        paymentsPanel = crudPanel(CrudSpecs.payments());
        paymentsPanel.addAction("Розрахувати суму", this::calculateFee);
        return paymentsPanel;
    }

    /** Розрахунок суми оплати за стратегією FeePolicy залежно від пільги учасника. */
    public void calculateFee(CrudPanel panel) {
        try {
            int enrollmentId = (Integer) panel.getValue("enrollment_id");
            FeeQuote quote = paymentController.calculateFee(enrollmentId);
            panel.setValue("amount", quote.getAmount());
            panel.showOk("<html>Тариф: " + quote.getMonthlyFee() + " грн; пільга: " + quote.getPolicyName()
                    + "<br>До сплати: " + quote.getAmount() + " грн</html>");
        } catch (IllegalArgumentException | ScheduleException | DataException ex) {
            panel.showError(ex.getMessage());
        }
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
