package bdt.ui;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.dao.DataException;
import bdt.model.Room;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * Діалог прецеденту «Додавання заняття до розкладу». Кожен крок діалогу викликає
 * відповідну системну операцію контролера: newEntry, selectSection, setTime, getFreeRooms,
 * selectRoom, saveEntry.
 */
public class AddEntryDialog extends JDialog {
    private final ScheduleController controller;
    private final JComboBox<Section> section = new JComboBox<>();
    private final JLabel sectionInfo = new JLabel(" ");
    private final JComboBox<String> day = new JComboBox<>(
            new String[]{"Понеділок", "Вівторок", "Середа", "Четвер", "П’ятниця", "Субота", "Неділя"});
    private final JTextField start = new JTextField("15:00", 6);
    private final JTextField end = new JTextField("16:30", 6);
    private final DefaultListModel<Room> roomsModel = new DefaultListModel<>();
    private final JList<Room> rooms = new JList<>(roomsModel);
    private final StatusLabel status = new StatusLabel();
    private final JButton save = new JButton("Зберегти в розкладі");
    private Consumer<ScheduleEntry> onSaved = e -> { };

    public AddEntryDialog(Window owner, ScheduleController controller, List<Section> sections) {
        super(owner, "Додавання заняття до розкладу", ModalityType.MODELESS);
        this.controller = controller;
        controller.newEntry();                                   // системна операція 1
        sections.forEach(section::addItem);
        section.setSelectedIndex(-1);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 12, 6, 12));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; form.add(step("1. Секція:"), g);
        g.gridx = 1; g.weightx = 1; form.add(section, g);
        g.gridx = 1; g.gridy = 1; form.add(sectionInfo, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0; form.add(step("2. День і час:"), g);
        JPanel time = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        time.add(day);
        time.add(new JLabel("  з"));
        time.add(start);
        time.add(new JLabel("до"));
        time.add(end);
        JButton check = new JButton("Перевірити керівника");
        time.add(check);
        g.gridx = 1; form.add(time, g);

        g.gridx = 0; g.gridy = 3; form.add(step("3. Приміщення:"), g);
        JButton find = new JButton("Підібрати вільні приміщення");
        g.gridx = 1; form.add(find, g);
        rooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rooms.setVisibleRowCount(6);
        g.gridx = 1; g.gridy = 4; g.weighty = 1; g.fill = GridBagConstraints.BOTH;
        form.add(new JScrollPane(rooms), g);

        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(BorderFactory.createEmptyBorder(0, 12, 10, 12));
        status.setFont(status.getFont().deriveFont(Font.BOLD));
        south.add(status, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancel = new JButton("Скасувати");
        btns.add(save);
        btns.add(cancel);
        south.add(btns, BorderLayout.SOUTH);
        save.setEnabled(false);

        section.addActionListener(e -> chooseSection());
        check.addActionListener(e -> checkTime());
        find.addActionListener(e -> findRooms());
        rooms.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && rooms.getSelectedValue() != null) chooseRoom();
        });
        save.addActionListener(e -> saveEntry());
        cancel.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        setSize(760, 470);
        setLocationRelativeTo(owner);
    }

    private static JLabel step(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
    }

    private void chooseSection() {
        Section s = (Section) section.getSelectedItem();
        if (s == null) return;
        try {
            controller.selectSection(s.getId());                 // системна операція 2
            sectionInfo.setText("Керівник: " + s.getLeader().getFullName() + ";  активних учасників: "
                    + controller.getParticipants() + " (макс. " + s.getMaxParticipants() + ")");
            roomsModel.clear();
            save.setEnabled(false);
            status.showInfo("Вкажіть день тижня та час заняття.");
        } catch (ScheduleException | DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    private void checkTime() {
        try {
            controller.setTime(day.getSelectedIndex() + 1,
                    LocalTime.parse(start.getText().trim()), LocalTime.parse(end.getText().trim())); // операція 3
            roomsModel.clear();
            save.setEnabled(false);
            status.showOk("Керівник вільний у цей час. Підберіть приміщення.");
        } catch (DateTimeParseException ex) {
            status.showError("Час вводиться у форматі ГГ:ХХ, наприклад 15:30.");
        } catch (ScheduleException ex) {
            status.showError(ex.getMessage());
        }
    }

    private void findRooms() {
        try {
            List<Room> free = controller.getFreeRooms();          // системна операція 4
            roomsModel.clear();
            free.forEach(roomsModel::addElement);
            if (free.isEmpty()) status.showError("Немає вільних приміщень потрібної місткості. Змініть час заняття.");
            else status.showInfo("Знайдено вільних приміщень: " + free.size() + ". Оберіть приміщення.");
        } catch (ScheduleException | DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    private void chooseRoom() {
        try {
            controller.selectRoom(rooms.getSelectedValue().getId()); // системна операція 5
            save.setEnabled(true);
            status.showOk("Приміщення " + rooms.getSelectedValue().getNumber() + " обрано. Підтвердіть збереження.");
        } catch (ScheduleException | DataException ex) {
            save.setEnabled(false);
            status.showError(ex.getMessage());
        }
    }

    private void saveEntry() {
        try {
            ScheduleEntry saved = controller.saveEntry();          // системна операція 6
            onSaved.accept(saved);
            dispose();
        } catch (ScheduleException | DataException ex) {
            status.showError(ex.getMessage());
        }
    }

    public void setOnSaved(Consumer<ScheduleEntry> onSaved) { this.onSaved = onSaved; }

    // ---- методи для автоматизованої демонстрації та знімків екрана ----
    public void demoSection(int index) { section.setSelectedIndex(index); }
    public void demoTime(int dayIndex, String from, String to) {
        day.setSelectedIndex(dayIndex);
        start.setText(from);
        end.setText(to);
        checkTime();
    }
    public void demoFindRooms() { findRooms(); }
    public void demoRoom(int index) { rooms.setSelectedIndex(index); }
    public void demoSave() { saveEntry(); }
}
