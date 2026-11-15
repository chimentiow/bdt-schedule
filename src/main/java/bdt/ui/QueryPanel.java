package bdt.ui;

import bdt.db.Database;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/** Вкладка «Запити»: виконання SQL-запитів до бази даних з параметрами. */
public class QueryPanel extends JPanel {
    private final Database db;
    private final JList<QueryDef> list;
    private final JPanel paramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final List<JTextField> paramFields = new ArrayList<>();
    private final JTextArea sqlText = new JTextArea(9, 60);
    private final DefaultTableModel result = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final StatusLabel status = new StatusLabel();

    public QueryPanel(Database db, List<QueryDef> queries) {
        super(new BorderLayout(6, 6));
        this.db = db;
        list = new JList<>(queries.toArray(new QueryDef[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showQuery();
        });
        sqlText.setEditable(false);
        sqlText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JButton run = new JButton("Виконати запит");
        run.addActionListener(e -> execute());

        JPanel right = new JPanel(new BorderLayout(4, 4));
        JPanel north = new JPanel(new BorderLayout());
        paramPanel.setBorder(BorderFactory.createTitledBorder("Параметри"));
        north.add(paramPanel, BorderLayout.NORTH);
        JScrollPane sqlScroll = new JScrollPane(sqlText);
        sqlScroll.setBorder(BorderFactory.createTitledBorder("Текст запиту SQL"));
        north.add(sqlScroll, BorderLayout.CENTER);
        JPanel runRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        runRow.add(run);
        runRow.add(status);
        north.add(runRow, BorderLayout.SOUTH);
        JTable table = new JTable(result);
        table.setRowHeight(22);
        JScrollPane resScroll = new JScrollPane(table);
        resScroll.setBorder(BorderFactory.createTitledBorder("Результат"));
        right.add(north, BorderLayout.NORTH);
        right.add(resScroll, BorderLayout.CENTER);

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBorder(BorderFactory.createTitledBorder("Запити"));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, right);
        split.setDividerLocation(400);
        add(split, BorderLayout.CENTER);
        list.setSelectedIndex(0);
    }

    private void showQuery() {
        QueryDef q = list.getSelectedValue();
        if (q == null) return;
        paramPanel.removeAll();
        paramFields.clear();
        for (String[] p : q.params()) {
            paramPanel.add(new JLabel(p[0] + ":"));
            JTextField f = new JTextField(p.length > 1 ? p[1] : "", Math.max(8, p.length > 1 ? p[1].length() : 8));
            paramFields.add(f);
            paramPanel.add(f);
        }
        if (q.params().isEmpty()) paramPanel.add(new JLabel("немає"));
        paramPanel.revalidate();
        paramPanel.repaint();
        sqlText.setText(q.sql());
        sqlText.setCaretPosition(0);
        result.setDataVector(new Object[0][], new Object[0]);
        status.clear();
    }

    public void execute() {
        QueryDef q = list.getSelectedValue();
        try (PreparedStatement ps = db.getConnection().prepareStatement(q.sql())) {
            int marks = ps.getParameterMetaData().getParameterCount();
            for (int i = 0; i < marks; i++) {
                // запит 9 використовує однакові параметри у кількох місцях – беремо по колу
                ps.setString(i + 1, paramFields.get(i % paramFields.size()).getText().trim());
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                Object[] cols = new Object[md.getColumnCount()];
                for (int i = 0; i < cols.length; i++) cols[i] = md.getColumnLabel(i + 1);
                result.setDataVector(new Object[0][], cols);
                while (rs.next()) {
                    Object[] row = new Object[cols.length];
                    for (int i = 0; i < cols.length; i++) row[i] = rs.getObject(i + 1);
                    result.addRow(row);
                }
            }
            status.showOk("Знайдено рядків: " + result.getRowCount());
        } catch (SQLException ex) {
            status.showError("Помилка: " + ex.getMessage().split("\n")[0]);
        }
    }

    public void select(int index) { list.setSelectedIndex(index); }
}
