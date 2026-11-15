package bdt.ui;

import bdt.db.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Опис SQL-запиту з файлу queries.sql: номер, назва, параметри зі значеннями за замовчуванням, текст. */
public record QueryDef(String number, String title, List<String[]> params, String sql) {

    public static List<QueryDef> loadAll() throws IOException {
        String text = Database.readResource("/queries.sql");
        List<QueryDef> list = new ArrayList<>();
        for (String block : text.split("-- @query ")) {
            if (block.isBlank()) continue;
            int nl = block.indexOf('\n');
            String[] head = block.substring(0, nl).split("\\|");
            List<String[]> params = new ArrayList<>();
            if (head.length > 2) {
                for (String p : head[2].split(";")) {
                    params.add(p.trim().split("=", 2));
                }
            }
            list.add(new QueryDef(head[0].trim(), head[1].trim(), params, block.substring(nl + 1).trim()));
        }
        return list;
    }

    @Override
    public String toString() { return "Запит " + number + ". " + title; }
}
