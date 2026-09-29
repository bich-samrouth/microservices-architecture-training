package co.khmer.samrouth.ecommerce.payment.persistence.repository;

import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditHistoryJpaRepository extends JpaRepository<CreditHistoryEntity , UUID> {

}
