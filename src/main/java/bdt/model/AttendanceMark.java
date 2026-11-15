package bdt.model;

/** Відмітка присутності учасника на занятті. */
public class AttendanceMark {
    private final int participantId;
    private final String participantName;
    private boolean present;

    public AttendanceMark(int participantId, String participantName, boolean present) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.present = present;
    }

    public int getParticipantId() { return participantId; }
    public String getParticipantName() { return participantName; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
}
