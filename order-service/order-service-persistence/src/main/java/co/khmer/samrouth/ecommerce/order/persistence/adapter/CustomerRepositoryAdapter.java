package co.khmer.samrouth.ecommerce.order.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.entity.Customer;
import co.khmer.samrouth.ecommerce.order.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.order.persistence.mapper.CustomerPersistenceMapper;
import co.khmer.samrouth.ecommerce.order.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    // Inject dependency
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository
                .findById(customerId)
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

}
