package co.khmer.samrouth.order.persistence.adapter;

import co.khmer.samrouth.domain.entity.Business;
import co.khmer.samrouth.order.domain.port.output.BusinessRepository;
import co.khmer.samrouth.order.persistence.mapper.OrderPersistenceMapper;
import co.khmer.samrouth.order.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    // Inject Dependency
    private final BusinessJpaRepository businessJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(UUID businessId) {
        return businessJpaRepository.findById(businessId)
                .map(orderPersistenceMapper::businessEntityToBusiness);
    }
}
