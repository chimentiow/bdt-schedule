package bdt.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Стратегія (Strategy) розрахунку щомісячної оплати занять залежно від пільги учасника.
 * Нова пільга додається новим класом без зміни коду, що використовує стратегію (Protected Variations).
 */
public interface FeePolicy {

    BigDecimal apply(BigDecimal monthlyFee);

    String getName();

    static FeePolicy forBenefit(String benefit) {
        if ("Багатодітна".equals(benefit)) return new DiscountPolicy("Багатодітна сім’я (−30%)", 30);
        if ("Пільгова".equals(benefit)) return new DiscountPolicy("Пільгова категорія (−50%)", 50);
        return new FullFeePolicy();
    }

    /** Оплата без знижки. */
    class FullFeePolicy implements FeePolicy {
        @Override
        public BigDecimal apply(BigDecimal monthlyFee) {
            return monthlyFee.setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public String getName() { return "Без пільг"; }
    }

    /** Оплата зі знижкою у відсотках. */
    class DiscountPolicy implements FeePolicy {
        private final String name;
        private final int percent;

        public DiscountPolicy(String name, int percent) {
            if (percent < 0 || percent > 100) {
                throw new IllegalArgumentException("Знижка має бути від 0 до 100%.");
            }
            this.name = name;
            this.percent = percent;
        }

        @Override
        public BigDecimal apply(BigDecimal monthlyFee) {
            BigDecimal k = BigDecimal.valueOf(100 - percent).divide(BigDecimal.valueOf(100));
            return monthlyFee.multiply(k).setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public String getName() { return name; }
    }
}
