package co.khmer.samrouth.order.domain.port.output;

import co.khmer.samrouth.domain.entity.Business;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository {
    Optional<Business> findBusiness(Business business);
}
