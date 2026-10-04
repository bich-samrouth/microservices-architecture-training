package co.khmer.samrouth.ecommerce.payment.persistence.adapter;

import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.PaymentEntity;
import co.khmer.samrouth.ecommerce.payment.persistence.mapper.PaymentPersistenceMapper;
import co.khmer.samrouth.ecommerce.payment.persistence.repository.PaymentJpaRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = paymentPersistenceMapper.paymentToPaymentEntity(payment);
        PaymentEntity saved = paymentJpaRepository.save(entity);
        return paymentPersistenceMapper.paymentEntityToPayment(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentJpaRepository.findById(id)
                .map(paymentPersistenceMapper::paymentEntityToPayment);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentPersistenceMapper::paymentEntityToPayment);
    }

    @Override
    public List<Payment> findAll() {
        return paymentPersistenceMapper.paymentEntityListToPaymentList(paymentJpaRepository.findAll());
    }
}
