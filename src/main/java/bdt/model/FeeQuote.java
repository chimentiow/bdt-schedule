package bdt.model;

import java.math.BigDecimal;

/** Розрахунок щомісячної оплати учасника: тариф секції, застосована пільга і сума до сплати. */
public final class FeeQuote {
    private final BigDecimal monthlyFee;
    private final FeePolicy policy;

    public FeeQuote(BigDecimal monthlyFee, String benefit) {
        this.monthlyFee = monthlyFee;
        this.policy = FeePolicy.forBenefit(benefit);
    }

    public BigDecimal getMonthlyFee() { return monthlyFee; }

    public String getPolicyName() { return policy.getName(); }

    /** Сума до сплати з урахуванням пільги. */
    public BigDecimal getAmount() { return policy.apply(monthlyFee); }
}
