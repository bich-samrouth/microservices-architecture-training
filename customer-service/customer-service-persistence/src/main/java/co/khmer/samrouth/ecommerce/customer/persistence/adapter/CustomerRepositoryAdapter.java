package co.khmer.samrouth.ecommerce.customer.persistence.adapter;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.persistence.entity.CustomerEntity;
import co.khmer.samrouth.ecommerce.customer.persistence.mapper.CustomerPersistenceMapper;
import co.khmer.samrouth.ecommerce.customer.persistence.repository.CustomerJpaRepository;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;


    @Cacheable(key = "'allCustomers'" ,value = "customer")
    @Override
    public PageResponse<CustomerResult> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerEntity> customerEntityPage = customerJpaRepository.findAll(pageable);
        Page<CustomerResult> customerResultPage = customerEntityPage.map(customerPersistenceMapper::toCustomerResult);
        return PageResponse.<CustomerResult> builder()
                .items(customerResultPage.getContent())
                .page(customerResultPage.getNumber())
                .size(customerResultPage.getSize())
                .totalPages(customerResultPage.getTotalPages())
                .totalElements(customerResultPage.getTotalElements())
                .hasNext(customerEntityPage.hasNext())
                .build();
    }

    @Cacheable(cacheNames = "customer", key = "#customerId.value")
    @Override
    public CustomerResult save(Customer customer) {
        CustomerEntity saveEntity = customerJpaRepository.save(
                customerPersistenceMapper.customerToCustomerEntity(customer)
        );
        return customerPersistenceMapper.customerEntityToCustomerResult(saveEntity);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return customerJpaRepository.findById(customerId.value())
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

    @Cacheable(cacheNames = "customer", key = "#customerId.value")
    @Override
    public Optional<CustomerResult> findByIdWithCache(CustomerId customerId) {
        return customerJpaRepository.findById(customerId.value())
                .map(customerPersistenceMapper::customerEntityToCustomerResult);
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(CustomerId customerId) {
        customerJpaRepository.deleteById(customerId.value());
    }
}
