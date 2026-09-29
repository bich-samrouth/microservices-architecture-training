package co.khmer.samrouth.ecommerce.payment.persistence.repository;

import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryEntity, UUID> {
}
