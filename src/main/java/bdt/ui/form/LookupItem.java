package bdt.ui.form;

/** Елемент списку-довідника: ідентифікатор запису та його назва. */
public record LookupItem(int id, String label) {
    @Override
    public String toString() { return label; }
}
