package co.khmer.samrouth.order.persistence.adapter;

import co.khmer.samrouth.domain.entity.Customer;
import co.khmer.samrouth.order.domain.port.output.CustomerRepository;
import co.khmer.samrouth.order.persistence.mapper.OrderPersistenceMapper;
import co.khmer.samrouth.order.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    // Inject dependency
    private final CustomerJpaRepository customerJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;
    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository
                .findById(customerId)
                .map(orderPersistenceMapper::customerEntityToCustomer);
    }

}
