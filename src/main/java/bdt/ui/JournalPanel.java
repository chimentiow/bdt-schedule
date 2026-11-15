package bdt.ui;

import bdt.controller.LessonController;
import bdt.controller.ScheduleException;
import bdt.dao.DataException;
import bdt.model.AttendanceMark;
import bdt.model.Lesson;
import bdt.model.LessonSummary;
import bdt.model.Section;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Вкладка «Журнал занять»: прецедент «Облік фактичного заняття». */
public class JournalPanel extends JPanel {
    private final LessonController controller;
    private final MainWindow main;
    private final JComboBox<Section> section = new JComboBox<>();
    private final JTextField date = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField topic = new JTextField(30);
    private final JLabel lessonInfo = new JLabel(" ");
    private final StatusLabel status = new StatusLabel();
    private final DefaultTableModel marks = new DefaultTableModel(new Object[]{"Присутній", "Учасник", "ID"}, 0) {
        @Override
        public Class<?> getColumnClass(int c) { return c == 0 ? Boolean.class : Object.class; }

        @Override
        public boolean isCellEditable(int r, int c) { return c == 0; }
    };
    private final DefaultTableModel history = new DefaultTableModel(
            new Object[]{"Дата", "Час", "Приміщення", "Тема", "Присутні"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };

    public JournalPanel(LessonController controller, MainWindow main) {
        super(new BorderLayout(6, 6));
        this.controller = controller;
        this.main = main;
        main.getSectionCatalog().findAll().forEach(section::addItem);

        JPanel top = new JPanel(new GridLayout(3, 1));
        JPanel r1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        r1.add(new JLabel("Секція:"));
        r1.add(section);
        r1.add(new JLabel("Дата (РРРР-ММ-ДД):"));
        r1.add(date);
        JButton open = new JButton("Відкрити заняття");
        r1.add(open);
        JPanel r2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        r2.add(lessonInfo);
        JPanel r3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        r3.add(new JLabel("Тема заняття:"));
        r3.add(topic);
        JButton close = new JButton("Зберегти заняття");
        r3.add(close);
        top.add(r1);
        top.add(r2);
        top.add(r3);

        JTable marksTable = new JTable(marks);
        marksTable.setRowHeight(24);
        marksTable.getColumnModel().getColumn(0).setMaxWidth(90);
        marksTable.getColumnModel().getColumn(2).setMaxWidth(50);
        marks.addTableModelListener(e -> {
            if (e.getColumn() == 0 && e.getFirstRow() >= 0 && controller.getLesson() != null) {
                int r = e.getFirstRow();
                controller.markAttendance((Integer) marks.getValueAt(r, 2), (Boolean) marks.getValueAt(r, 0));
            }
        });
        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createTitledBorder("Відвідування"));
        left.add(new JScrollPane(marksTable));
        JTable histTable = new JTable(history);
        histTable.setRowHeight(24);
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Проведені заняття секції"));
        right.add(new JScrollPane(histTable));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.4);

        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        section.addActionListener(e -> loadHistory());
        open.addActionListener(e -> openLesson());
        close.addActionListener(e -> closeLesson());
        loadHistory();
    }

    public void openLesson() {
        Section s = (Section) section.getSelectedItem();
        try {
            Lesson l = controller.openLesson(s.getId(), LocalDate.parse(date.getText().trim()));
            marks.setRowCount(0);
            for (AttendanceMark m : l.getMarks()) {
                marks.addRow(new Object[]{m.isPresent(), m.getParticipantName(), m.getParticipantId()});
            }
            lessonInfo.setText("За розкладом: " + l.getEntry().getSlot() + ", приміщення "
                    + l.getEntry().getRoom().getNumber() + ", керівник "
                    + l.getEntry().getSection().getLeader().getShortName()
                    + ".  Учасників у списку: " + l.getMarks().size());
            status.showOk("Заняття відкрито. Відмітьте відсутніх і вкажіть тему.");
        } catch (DateTimeParseException ex) {
            status.showError("Дата вводиться у форматі РРРР-ММ-ДД.");
        } catch (ScheduleException | DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    public void closeLesson() {
        try {
            controller.setTopic(topic.getText());
            int total = marks.getRowCount();
            int present = controller.closeLesson();
            status.showOk("Заняття збережено. Присутні: " + present + " з " + total + ".");
            marks.setRowCount(0);
            topic.setText("");
            lessonInfo.setText(" ");
            loadHistory();
            main.refreshStatus();
        } catch (ScheduleException | DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    private void loadHistory() {
        history.setRowCount(0);
        Section s = (Section) section.getSelectedItem();
        if (s == null) return;
        try {
            for (LessonSummary l : controller.getHistory(s.getId())) {
                history.addRow(new Object[]{l.getDate(), l.getStartText(), l.getRoomNumber(), l.getTopic(),
                        l.getAttendance()});
            }
        } catch (DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    // ---- для автоматизованої демонстрації ----
    public void demo(int sectionIndex, String day, String lessonTopic) {
        section.setSelectedIndex(sectionIndex);
        date.setText(day);
        topic.setText(lessonTopic);
    }

    public void demoAbsent(int row) { marks.setValueAt(Boolean.FALSE, row, 0); }
}
