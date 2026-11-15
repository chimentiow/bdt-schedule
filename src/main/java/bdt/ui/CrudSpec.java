package bdt.ui;

import bdt.ui.form.FormField;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Опис довідника для панелі CrudPanel: заголовок форми, таблиця БД, стовпець-ідентифікатор,
 * запит для списку записів і поля форми. Формує SQL-команди для своєї таблиці.
 */
public record CrudSpec(String title, String table, String idColumn, String listSql, List<FormField> fields) {

    /** Запит значень полів одного запису. */
    public String selectSql() {
        return "SELECT " + columnList() + " FROM " + table + whereId();
    }

    public String insertSql() {
        String marks = fields.stream().map(f -> "?").collect(Collectors.joining(", "));
        return "INSERT INTO " + table + " (" + columnList() + ") VALUES (" + marks + ")";
    }

    public String updateSql() {
        String assignments = fields.stream().map(f -> f.getColumn() + " = ?").collect(Collectors.joining(", "));
        return "UPDATE " + table + " SET " + assignments + whereId();
    }

    public String deleteSql() {
        return "DELETE FROM " + table + whereId();
    }

    private String columnList() {
        return fields.stream().map(FormField::getColumn).collect(Collectors.joining(", "));
    }

    private String whereId() {
        return " WHERE " + idColumn + " = ?";
    }
}
