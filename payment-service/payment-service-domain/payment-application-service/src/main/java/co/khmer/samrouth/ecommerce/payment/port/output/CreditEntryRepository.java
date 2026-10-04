package co.khmer.samrouth.ecommerce.payment.port.output;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
