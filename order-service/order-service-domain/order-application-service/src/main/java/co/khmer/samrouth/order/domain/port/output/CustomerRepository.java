package co.khmer.samrouth.order.domain.port.output;

import co.khmer.samrouth.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
