package bdt.model;

/** Керівник секції. */
public class Leader {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final String middleName;

    public Leader(int id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public int getId() { return id; }

    public String getShortName() {
        return lastName + " " + firstName.charAt(0) + "." + (middleName == null || middleName.isEmpty()
                ? "" : " " + middleName.charAt(0) + ".");
    }

    public String getFullName() {
        return lastName + " " + firstName + (middleName == null ? "" : " " + middleName);
    }

    @Override
    public String toString() { return getShortName(); }
}
