package co.khmer.samrouth.ecommerce.business.persistence.adapter;

import co.khmer.samrouth.ecommerce.business.entity.OrderApproval;
import co.khmer.samrouth.ecommerce.business.persistence.entity.OrderApprovalEntity;
import co.khmer.samrouth.ecommerce.business.persistence.mapper.OrderApprovalPersistenceMapper;
import co.khmer.samrouth.ecommerce.business.persistence.repository.OrderApprovalJpaRepository;
import co.khmer.samrouth.ecommerce.business.port.output.OrderApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderApprovalRepositoryAdapter implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final OrderApprovalPersistenceMapper orderApprovalPersistenceMapper;

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        OrderApprovalEntity orderApprovalEntity = orderApprovalPersistenceMapper.orderApprovalToOrderApprovalEntity(orderApproval);

        return orderApprovalPersistenceMapper.orderApprovalEntityToOrderApproval(orderApprovalJpaRepository.save(orderApprovalEntity));
    }
}
