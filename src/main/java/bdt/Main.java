package bdt;

import bdt.db.Database;
import bdt.ui.MainWindow;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/** Точка входу: створює (за потреби) базу даних і відкриває головне вікно. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        setupLookAndFeel();
        Database db = Database.fileDatabase();
        try {
            db.initialize();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Не вдалося відкрити базу даних:\n" + e.getMessage(),
                    "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(db::close));
        SwingUtilities.invokeLater(() -> {
            try {
                new MainWindow(db).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void setupLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ignored) {
            // залишається стандартний вигляд
        }
        FontUIResource font = new FontUIResource(Font.SANS_SERIF, Font.PLAIN, 13);
        UIManager.getLookAndFeelDefaults().put("defaultFont", font);
    }
}
