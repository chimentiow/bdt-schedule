package bdt.ui;

import java.awt.Color;
import javax.swing.JLabel;

/** Рядок повідомлень вікна або панелі: помилка – червоним, успіх – зеленим, підказка – сірим. */
public class StatusLabel extends JLabel {
    private static final Color ERROR_COLOR = new Color(180, 0, 0);
    private static final Color OK_COLOR = new Color(0, 120, 0);
    private static final Color INFO_COLOR = Color.DARK_GRAY;
    private static final String EMPTY = " ";

    public StatusLabel() {
        super(EMPTY);
    }

    public void showError(String message) { display(ERROR_COLOR, message); }

    public void showOk(String message) { display(OK_COLOR, message); }

    public void showInfo(String message) { display(INFO_COLOR, message); }

    public void clear() { setText(EMPTY); }

    private void display(Color color, String message) {
        setForeground(color);
        setText(message);
    }
}
