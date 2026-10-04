package co.khmer.samrouth.ecommerce.customer.persistence.adapter;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.persistence.entity.CustomerEntity;
import co.khmer.samrouth.ecommerce.customer.persistence.mapper.CustomerPersistenceMapper;
import co.khmer.samrouth.ecommerce.customer.persistence.repository.CustomerJpaRepository;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = customerPersistenceMapper.customerToCustomerEntity(customer);
        CustomerEntity savedEntity = customerJpaRepository.save(customerEntity);
        return customerPersistenceMapper.customerEntityToCustomer(savedEntity);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return customerJpaRepository.findByUsername(username)
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerJpaRepository.findByEmail(email)
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return customerPersistenceMapper.customerEntityListToCustomerList(customerJpaRepository.findAll());
    }

    @Override
    public void deleteById(UUID customerId) {
        customerJpaRepository.deleteById(customerId);
    }

    @Override
    public boolean existsById(UUID customerId) {
        return customerJpaRepository.existsById(customerId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }
}
