package co.khmer.samrouth.ecommerce.payment.entity;

import co.khmer.samrouth.ecommerce.order.entity.AggregateRoot;
import co.khmer.samrouth.ecommerce.order.valueobject.*;
import co.khmer.samrouth.ecommerce.payment.excetion.PaymentDomainException;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Payment extends AggregateRoot<PaymentId> {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money price;

    private ZonedDateTime createdAt;
    private PaymentStatus paymentStatus;
    private ZonedDateTime updatedAt;

    private Payment(Builder builder) {
        super.setId(builder.id);
        orderId = builder.orderId;
        customerId = builder.customerId;
        price = builder.price;
        paymentStatus = builder.paymentStatus;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }


    public static Builder builder() {
        return new Builder();
    }

    public void validatePayment(){
        validateInitialPayment();
        validateTotalPrice();
    }

    public void  initializePayment(){
        validateInitialPayment();
        setId(new PaymentId(UUID.randomUUID()));
        paymentStatus = PaymentStatus.PENDING;
        createdAt = ZonedDateTime.now();
    }

    public void updateStatus(){
        validateInitialPayment();
        switch (paymentStatus) {
            case COMPLETED -> complete();
            case CANCELLED -> cancel();
            case FAILED -> fail();
            case PENDING -> throw new PaymentDomainException(
                    "Payment cannot be changed back to PENDING"
            );
        }
    }

    // Validate Initialize Payment
    private void validateInitialPayment(){
        if(paymentStatus != null || super.getId() != null){
            throw new PaymentDomainException("Payment is not in correct status for initialization");
        }
    }

    // Validate Price For Payment
    private void validateTotalPrice(){
        if(price == null || !price.isGreaterThanZero()){
            throw new PaymentDomainException("Total price must be greeter than zero");
        }
    }

    // Completed
    public void complete() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new PaymentDomainException(
                    "Payment is not in correct state for complete operation"
            );
        }

        paymentStatus = PaymentStatus.COMPLETED;
        updatedAt = ZonedDateTime.now();
    }

    // Cancel
    public void cancel() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new PaymentDomainException(
                    "Payment is not in correct state for cancel operation"
            );
        }

        paymentStatus = PaymentStatus.CANCELLED;
        updatedAt = ZonedDateTime.now();
    }

    // Fail
    public void fail() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new PaymentDomainException(
                    "Payment is not in correct state for fail operation"
            );
        }

        paymentStatus = PaymentStatus.FAILED;
        updatedAt = ZonedDateTime.now();
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static final class Builder {
        private PaymentId id;
        private OrderId orderId;
        private CustomerId customerId;
        private Money price;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

        private Builder() {
        }

        public Builder id(PaymentId val) {
            id = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(ZonedDateTime val) {
            updatedAt = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
