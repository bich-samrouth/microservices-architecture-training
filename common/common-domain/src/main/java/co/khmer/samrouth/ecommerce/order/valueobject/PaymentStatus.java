package co.khmer.samrouth.ecommerce.order.valueobject;

public enum PaymentStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    FAILED;

    // The whole lifecycle in one place
    public boolean canTransitionTo(PaymentStatus target) {
        return switch (this) {
            case PENDING   -> target == COMPLETED || target == CANCELLED || target == FAILED;
            case COMPLETED -> target == CANCELLED;   // refund
            case CANCELLED, FAILED -> false;         // final states
        };
    }
}
