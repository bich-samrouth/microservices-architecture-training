package co.khmer.samrouth.ecommerce.customer.persistence.repository;

import co.khmer.samrouth.ecommerce.customer.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByUsername(String username);
    Optional<CustomerEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
