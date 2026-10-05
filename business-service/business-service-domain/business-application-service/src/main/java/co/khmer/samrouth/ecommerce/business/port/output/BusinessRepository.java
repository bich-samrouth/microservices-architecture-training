package co.khmer.samrouth.ecommerce.business.port.output;

import co.khmer.samrouth.ecommerce.business.entity.Business;

import java.util.Optional;

public interface BusinessRepository {
    Optional<Business> findBusinessInformation(Business business);
}
