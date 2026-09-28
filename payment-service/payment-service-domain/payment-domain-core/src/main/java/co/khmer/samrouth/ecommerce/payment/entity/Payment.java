package co.khmer.samrouth.ecommerce.payment.entity;

import co.khmer.samrouth.ecommerce.order.entity.AggregateRoot;
import co.khmer.samrouth.ecommerce.order.valueobject.*;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentDomainException;

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
        validateTotalPrice();
    }

    public void  initializePayment(){
        validateIsNew();
        setId(new PaymentId(UUID.randomUUID()));
        paymentStatus = PaymentStatus.PENDING;
        createdAt = ZonedDateTime.now();
    }

    public void updateStatus(PaymentStatus newStatus) {
        validateIsInitialized();

        switch (newStatus) {
            case COMPLETED -> complete();
            case CANCELLED -> cancel();
            case FAILED    -> fail();
            default -> throw new PaymentDomainException(
                    "Payment cannot be changed to " + newStatus);
        }
    }

    // Completed
    public void complete() {
        transitionFromPending(PaymentStatus.COMPLETED, "complete");
    }

    // Cancel
    public void cancel() {
        transitionFromPending(PaymentStatus.CANCELLED, "cancel");
    }

    // Fail
    public void fail() {
        transitionFromPending(PaymentStatus.FAILED, "fail");
    }

    private void transitionFromPending(PaymentStatus target, String operation) {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new PaymentDomainException(
                    "Payment is not in correct state for " + operation + " operation");
        }
        paymentStatus = target;
        updatedAt = ZonedDateTime.now();
    }

    // Payment has no id and no status yet
    private void validateIsNew(){
        if(paymentStatus != null || super.getId() != null){
            throw new PaymentDomainException("Payment is not in correct status for initialization");
        }
    }

    // Payment must already have an id and status
    private void validateIsInitialized() {
        if (paymentStatus == null || getId() == null) {
            throw new PaymentDomainException("Payment is not initialized");
        }
    }

    // Validate Price For Payment
    private void validateTotalPrice(){
        if(price == null || !price.isGreaterThanZero()){
            throw new PaymentDomainException("Total price must be greeter than zero");
        }
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
