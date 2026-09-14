package co.khmer.samrouth.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public final static Money ZERO = new Money(BigDecimal.ZERO);

    // validation logic money is greater than zero
    public boolean isGreaterThanZero() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    // validation logic money is greater than another money
    public boolean isGreaterThan(Money money) {
        return amount.compareTo(money.amount) > 0;
    }

    // add money to another money
    public Money add(Money money) {
        return new Money(setScale(this.amount.add(money.amount)));
    }

    // subtract money from another money
    public Money subtract(Money money) {
        return new Money(setScale(this.amount.subtract(money.amount)));
    }

    // multiply money by a number
    public Money multiply(int multiplier){
        return new Money(setScale(this.amount.multiply(BigDecimal.valueOf(multiplier))));
    }

    // method setScale
    private BigDecimal setScale(BigDecimal inputAmount){
        return inputAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

}
