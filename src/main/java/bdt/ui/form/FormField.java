package bdt.ui.form;

import bdt.db.Database;
import java.sql.SQLException;
import javax.swing.JComponent;

/**
 * Поле форми довідника: підпис, стовпець таблиці БД і ознака обов’язковості.
 * Кожен вид поля (текст, число, дата, список, довідник) – окремий підклас, який сам
 * створює свій компонент редагування, показує в ньому значення і зчитує його назад.
 */
public abstract class FormField {
    private final String label;
    private final String column;
    private final boolean required;

    protected FormField(String label, String column, boolean required) {
        this.label = label;
        this.column = column;
        this.required = required;
    }

    public static FormField text(String label, String column) {
        return new StringField(label, column, true);
    }

    public static FormField optText(String label, String column) {
        return new StringField(label, column, false);
    }

    public static FormField integer(String label, String column) {
        return new IntegerField(label, column);
    }

    public static FormField decimal(String label, String column) {
        return new DecimalField(label, column);
    }

    public static FormField date(String label, String column) {
        return new DateField(label, column);
    }

    public static FormField choice(String label, String column, String... options) {
        return new ChoiceField(label, column, options);
    }

    public static FormField lookup(String label, String column, String sql) {
        return new LookupField(label, column, sql);
    }

    /** Створює компонент редагування поля. */
    public abstract JComponent createEditor();

    /** Показує в компоненті значення, прочитане зі стовпця таблиці. */
    public abstract void show(JComponent editor, Object value);

    /** Зчитує значення з компонента і перетворює його до типу стовпця. */
    public abstract Object read(JComponent editor);

    /** Повертає компонент до початкового стану (форма нового запису). */
    public abstract void clear(JComponent editor);

    /** Оновлює дані компонента з бази даних; потрібно лише полям-довідникам. */
    public void refresh(JComponent editor, Database db) throws SQLException {
        // для більшості полів оновлювати нічого
    }

    public String getLabel() { return label; }
    public String getColumn() { return column; }
    public boolean isRequired() { return required; }

    /** Підпис на формі; обов’язкові поля позначаються зірочкою. */
    public String getCaption() { return label + (required ? ": *" : ":"); }
}
