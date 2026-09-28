package co.khmer.samrouth.ecommerce.payment.entity;

import co.khmer.samrouth.ecommerce.order.entity.AggregateRoot;
import co.khmer.samrouth.ecommerce.order.valueobject.CreditEntryId;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.Money;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentDomainException;

public class CreditEntry extends AggregateRoot<CreditEntryId> {
    private final CustomerId customerId;
    private Money totalCreditAmount;

    private CreditEntry(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void addCreditAmount(Money amount){
        if (amount == null) {
            throw new PaymentDomainException(
                    "Credit amount is required"
            );
        }

        if (amount.isGreaterThanZero()) {
            throw new PaymentDomainException(
                    "Credit amount must be greater than zero"
            );
        }

        totalCreditAmount = totalCreditAmount.add(amount);
    }
    public void subtractCreditAmount(Money amount){
        if (amount == null) {
            throw new PaymentDomainException(
                    "Credit amount is required"
            );
        }

        if (amount.isGreaterThanZero()) {
            throw new PaymentDomainException(
                    "Credit amount must be greater than zero"
            );
        }

        if (totalCreditAmount.isGreaterThan(amount)) {
            throw new PaymentDomainException(
                    "Insufficient credit amount"
            );
        }

        totalCreditAmount = totalCreditAmount.subtract(amount);
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public static final class Builder {
        private CreditEntryId id;
        private CustomerId customerId;
        private Money totalCreditAmount;

        private Builder() {
        }

        public Builder id(CreditEntryId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
