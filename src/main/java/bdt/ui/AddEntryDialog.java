package bdt.ui;

import bdt.controller.ScheduleController;
import bdt.controller.ScheduleException;
import bdt.dao.DataException;
import bdt.model.Room;
import bdt.model.ScheduleEntry;
import bdt.model.Section;
import bdt.model.TimeSlot;
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
    private final JComboBox<String> day = new JComboBox<>(TimeSlot.dayNames());
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
        section.addActionListener(e -> chooseSection());
        save.setEnabled(false);
        save.addActionListener(e -> saveEntry());

        setLayout(new BorderLayout());
        add(buildForm(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
        setSize(760, 470);
        setLocationRelativeTo(owner);
    }

    /** Форма з трьох кроків: секція, день і час, приміщення. */
    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 12, 6, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; form.add(stepLabel("1. Секція:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; form.add(section, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(sectionInfo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; form.add(stepLabel("2. День і час:"), gbc);
        gbc.gridx = 1; form.add(buildTimeRow(), gbc);

        gbc.gridx = 0; gbc.gridy = 3; form.add(stepLabel("3. Приміщення:"), gbc);
        gbc.gridx = 1; form.add(button("Підібрати вільні приміщення", this::findRooms), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        form.add(buildRoomList(), gbc);
        return form;
    }

    private JPanel buildTimeRow() {
        JPanel time = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        time.add(day);
        time.add(new JLabel("  з"));
        time.add(start);
        time.add(new JLabel("до"));
        time.add(end);
        time.add(button("Перевірити керівника", this::checkTime));
        return time;
    }

    private JScrollPane buildRoomList() {
        rooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rooms.setVisibleRowCount(6);
        rooms.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && rooms.getSelectedValue() != null) chooseRoom();
        });
        return new JScrollPane(rooms);
    }

    /** Рядок повідомлень і кнопки «Зберегти» та «Скасувати». */
    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 12, 10, 12));
        status.setFont(status.getFont().deriveFont(Font.BOLD));
        bottom.add(status, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(save);
        buttons.add(button("Скасувати", this::dispose));
        bottom.add(buttons, BorderLayout.SOUTH);
        return bottom;
    }

    private static JLabel stepLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }

    private static JButton button(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
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
