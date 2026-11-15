package bdt.model;

/** Приміщення будинку дитячої творчості. */
public class Room {
    private final int id;
    private final String number;
    private final String type;
    private final int capacity;

    public Room(int id, String number, String type, int capacity) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.capacity = capacity;
    }

    /** Інформаційний експерт: чи вміщує приміщення задану кількість учасників. */
    public boolean fits(int participants) {
        return capacity >= participants;
    }

    public int getId() { return id; }
    public String getNumber() { return number; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() { return number + " (" + type + ", " + capacity + " місць)"; }
}
