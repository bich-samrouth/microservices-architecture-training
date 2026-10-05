package co.khmer.samrouth.ecommerce.business.persistence.repository;

import co.khmer.samrouth.ecommerce.business.persistence.entity.OrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderApprovalJpaRepository extends JpaRepository<OrderApprovalEntity, UUID> {
}
