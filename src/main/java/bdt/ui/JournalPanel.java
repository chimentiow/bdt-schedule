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
    private static final int ROW_HEIGHT = 24;
    private static final int PRESENT_COLUMN = 0;
    private static final int ID_COLUMN = 2;

    private final LessonController controller;
    private final MainWindow main;
    private final JComboBox<Section> section = new JComboBox<>();
    private final JTextField date = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField topic = new JTextField(30);
    private final JLabel lessonInfo = new JLabel(" ");
    private final StatusLabel status = new StatusLabel();
    private final DefaultTableModel marks = new DefaultTableModel(new Object[]{"Присутній", "Учасник", "ID"}, 0) {
        @Override
        public Class<?> getColumnClass(int column) { return column == PRESENT_COLUMN ? Boolean.class : Object.class; }

        @Override
        public boolean isCellEditable(int row, int column) { return column == PRESENT_COLUMN; }
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

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildAttendancePanel(), buildHistoryPanel());
        split.setResizeWeight(0.4);
        add(buildTopPanel(), BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        section.addActionListener(e -> loadHistory());
        loadHistory();
    }

    /** Верхня частина: вибір секції й дати, відомості про заняття, тема. */
    private JPanel buildTopPanel() {
        JPanel selectionRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionRow.add(new JLabel("Секція:"));
        selectionRow.add(section);
        selectionRow.add(new JLabel("Дата (РРРР-ММ-ДД):"));
        selectionRow.add(date);
        selectionRow.add(button("Відкрити заняття", this::openLesson));

        JPanel infoRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoRow.add(lessonInfo);

        JPanel topicRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topicRow.add(new JLabel("Тема заняття:"));
        topicRow.add(topic);
        topicRow.add(button("Зберегти заняття", this::closeLesson));

        JPanel top = new JPanel(new GridLayout(3, 1));
        top.add(selectionRow);
        top.add(infoRow);
        top.add(topicRow);
        return top;
    }

    /** Таблиця відміток присутності; зміна прапорця одразу передається контролеру. */
    private JPanel buildAttendancePanel() {
        JTable marksTable = new JTable(marks);
        marksTable.setRowHeight(ROW_HEIGHT);
        marksTable.getColumnModel().getColumn(PRESENT_COLUMN).setMaxWidth(90);
        marksTable.getColumnModel().getColumn(ID_COLUMN).setMaxWidth(50);
        marks.addTableModelListener(e -> {
            if (e.getColumn() == PRESENT_COLUMN && e.getFirstRow() >= 0 && controller.getLesson() != null) {
                int row = e.getFirstRow();
                controller.markAttendance((Integer) marks.getValueAt(row, ID_COLUMN),
                        (Boolean) marks.getValueAt(row, PRESENT_COLUMN));
            }
        });
        return titledPanel("Відвідування", marksTable);
    }

    private JPanel buildHistoryPanel() {
        JTable historyTable = new JTable(history);
        historyTable.setRowHeight(ROW_HEIGHT);
        return titledPanel("Проведені заняття секції", historyTable);
    }

    private static JPanel titledPanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table));
        return panel;
    }

    private static JButton button(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
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
                    + l.getEntry().getRoom().getNumber() + ", керівник " + l.getEntry().getLeaderName()
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

    public void demoAbsent(int row) { marks.setValueAt(Boolean.FALSE, row, PRESENT_COLUMN); }
}
