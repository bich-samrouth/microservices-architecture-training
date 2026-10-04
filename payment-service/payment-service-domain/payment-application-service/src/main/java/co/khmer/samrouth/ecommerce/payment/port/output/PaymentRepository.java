package co.khmer.samrouth.ecommerce.payment.port.output;

import co.khmer.samrouth.ecommerce.payment.entity.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
    Optional<Payment> findByOrderId(UUID orderId);
    List<Payment> findAll();
}
