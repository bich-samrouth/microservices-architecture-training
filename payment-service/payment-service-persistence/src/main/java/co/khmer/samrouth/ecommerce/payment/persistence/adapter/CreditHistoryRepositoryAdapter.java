package co.khmer.samrouth.ecommerce.payment.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditHistoryEntity;
import co.khmer.samrouth.ecommerce.payment.persistence.mapper.PaymentPersistenceMapper;
import co.khmer.samrouth.ecommerce.payment.persistence.repository.CreditHistoryJpaRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CreditHistoryRepositoryAdapter implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        CreditHistoryEntity entity = paymentPersistenceMapper.creditHistoryToCreditHistoryEntity(creditHistory);
        CreditHistoryEntity saved = creditHistoryJpaRepository.save(entity);
        return paymentPersistenceMapper.creditHistoryEntityToCreditHistory(saved);
    }

    @Override
    public List<CreditHistory> findByCustomerId(CustomerId customerId) {
        return paymentPersistenceMapper.creditHistoryEntityListToCreditHistoryList(
                creditHistoryJpaRepository.findByCustomerId(customerId.value())
        );
    }
}
