package co.khmer.samrouth.ecommerce.order.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.entity.Business;
import co.khmer.samrouth.ecommerce.order.port.output.BusinessRepository;
import co.khmer.samrouth.ecommerce.order.persistence.entity.BusinessEntity;
import co.khmer.samrouth.ecommerce.order.persistence.mapper.BusinessPersistenceMapper;
import co.khmer.samrouth.ecommerce.order.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    // Inject Dependency
    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessPersistenceMapper businessPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(Business business) {
        // Map business to list of business products
        List<UUID> businessProducts = businessPersistenceMapper.businessToBusinessProducts(business);

        // Find business entities from database
        List<BusinessEntity> businessEntities = businessJpaRepository.findByBusinessIdAndProductIdIn(
                business.getId().value(),
                businessProducts
        );
        // Map list of business entities to business which contains all products
        return Optional.of(businessPersistenceMapper.businessEntityToBusiness(businessEntities));
    }
}
