package co.khmer.samrouth.ecommerce.order.port.output;

import co.khmer.samrouth.ecommerce.order.entity.Business;

import java.util.Optional;

public interface BusinessRepository {
    Optional<Business> findBusiness(Business business);
}
