package co.khmer.samrouth.ecommerce.payment.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditEntryEntity;
import co.khmer.samrouth.ecommerce.payment.persistence.mapper.PaymentPersistenceMapper;
import co.khmer.samrouth.ecommerce.payment.persistence.repository.CreditEntryJpaRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CreditEntryRepositoryAdapter implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        CreditEntryEntity entity = paymentPersistenceMapper.creditEntryToCreditEntryEntity(creditEntry);
        CreditEntryEntity saved = creditEntryJpaRepository.save(entity);
        return paymentPersistenceMapper.creditEntryEntityToCreditEntry(saved);
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(CustomerId customerId) {
        return creditEntryJpaRepository.findByCustomerId(customerId.value())
                .map(paymentPersistenceMapper::creditEntryEntityToCreditEntry);
    }
}
