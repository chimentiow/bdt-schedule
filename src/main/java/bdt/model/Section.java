package bdt.model;

import java.math.BigDecimal;

/** Секція (гурток) будинку дитячої творчості. */
public class Section {
    private final int id;
    private final String name;
    private final String direction;
    private final Leader leader;
    private final int maxParticipants;
    private final BigDecimal monthlyFee;

    public Section(int id, String name, String direction, Leader leader, int maxParticipants,
                   BigDecimal monthlyFee) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.leader = leader;
        this.maxParticipants = maxParticipants;
        this.monthlyFee = monthlyFee;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDirection() { return direction; }
    public Leader getLeader() { return leader; }
    public int getMaxParticipants() { return maxParticipants; }
    public BigDecimal getMonthlyFee() { return monthlyFee; }

    @Override
    public String toString() { return name; }
}
