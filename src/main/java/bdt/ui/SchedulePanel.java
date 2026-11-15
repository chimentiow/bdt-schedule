package bdt.ui;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.model.ScheduleEntry;
import bdt.model.TimeSlot;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/** Вкладка «Розклад»: тижневий розклад занять і запуск прецеденту «Додавання заняття до розкладу». */
public class SchedulePanel extends JPanel {
    private final ScheduleController controller;
    private final MainWindow main;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "День", "Час", "Секція", "Керівник", "Приміщення"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JComboBox<String> dayFilter = new JComboBox<>(
            new String[]{"Усі дні", "Понеділок", "Вівторок", "Середа", "Четвер", "П’ятниця", "Субота", "Неділя"});
    private final JLabel status = new JLabel(" ");

    public SchedulePanel(ScheduleController controller, MainWindow main) {
        super(new BorderLayout(6, 6));
        this.controller = controller;
        this.main = main;
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Показати:"));
        top.add(dayFilter);
        JButton add = new JButton("Додати заняття до розкладу…");
        JButton del = new JButton("Видалити запис");
        top.add(add);
        top.add(del);
        dayFilter.addActionListener(e -> reload());
        add.addActionListener(e -> openAddDialog());
        del.addActionListener(e -> deleteSelected());
        table.setRowHeight(24);
        table.getColumnModel().getColumn(0).setMaxWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        reload();
    }

    public void reload() {
        model.setRowCount(0);
        int day = dayFilter.getSelectedIndex();
        for (ScheduleEntry e : controller.getSchedule().getEntries()) {
            if (day > 0 && e.getSlot().getDay() != day) continue;
            model.addRow(new Object[]{e.getId(), TimeSlot.dayName(e.getSlot().getDay()), e.getSlot().timeText(),
                    e.getSection().getName(), e.getSection().getLeader().getShortName(),
                    e.getRoom().getNumber() + " – " + e.getRoom().getType()});
        }
        status.setForeground(Color.DARK_GRAY);
        status.setText("Записів у розкладі: " + model.getRowCount());
    }

    public AddEntryDialog openAddDialog() {
        Window w = SwingUtilities.getWindowAncestor(this);
        AddEntryDialog d = new AddEntryDialog(w, controller, main.getSectionCatalog().findAll());
        d.setOnSaved(saved -> {
            reload();
            status.setForeground(new Color(0, 120, 0));
            status.setText("Додано: " + saved.describe());
            main.refreshStatus();
        });
        return d;
    }

    public void selectDay(int index) { dayFilter.setSelectedIndex(index); }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            status.setForeground(new Color(180, 0, 0));
            status.setText("Оберіть запис розкладу.");
            return;
        }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Видалити запис розкладу № " + id + "?", "Підтвердження",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                controller.deleteEntry(id);
                reload();
            } catch (ScheduleException | bdt.dao.DataException ex) {
                status.setForeground(new Color(180, 0, 0));
                status.setText(ex.getMessage());
            }
        }
    }
}
