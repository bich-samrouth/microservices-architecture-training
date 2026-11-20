package co.khmer.samrouth.ecommerce.customer.port.output;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    PageResponse<CustomerResult> findAll(int page, int size);

    CustomerResult save(Customer customer);

    Optional<Customer> findById(CustomerId customerId);

    Optional<CustomerResult> findByIdWithCache(CustomerId customerId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteById(CustomerId customerId);
}
