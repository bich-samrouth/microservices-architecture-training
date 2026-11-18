package co.khmer.samrouth.ecommerce.customer.port.output;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    PageResponse<CustomerResult> findAll(int page, int size);
    void deleteById(UUID customerId);
    boolean existsById(UUID customerId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
