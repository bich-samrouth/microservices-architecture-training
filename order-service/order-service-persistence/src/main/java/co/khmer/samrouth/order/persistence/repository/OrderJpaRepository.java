package co.khmer.samrouth.order.persistence.repository;

import co.khmer.samrouth.order.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// Benefit of using Spring Date JPA
// 1. Boilerplate code
// 2. Abstraction Repository
// 3. Derive Query Method (Auto generate SQL)
// 4. Object Relational Mapping (ORM) Hibernate
// 5. Specification (dynamic query)
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> getAll();
}
