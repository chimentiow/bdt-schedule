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

    /** Прізвище та ініціали, наприклад «Мельник А. В.». */
    public String getShortName() {
        String initials = firstName.charAt(0) + ".";
        if (hasMiddleName()) {
            initials += " " + middleName.charAt(0) + ".";
        }
        return lastName + " " + initials;
    }

    public String getFullName() {
        String fullName = lastName + " " + firstName;
        return middleName == null ? fullName : fullName + " " + middleName;
    }

    private boolean hasMiddleName() {
        return middleName != null && !middleName.isEmpty();
    }

    @Override
    public String toString() { return getShortName(); }
}
