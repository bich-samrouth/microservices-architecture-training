package co.khmer.samrouth.ecommerce.order.port.output;

import co.khmer.samrouth.ecommerce.order.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
