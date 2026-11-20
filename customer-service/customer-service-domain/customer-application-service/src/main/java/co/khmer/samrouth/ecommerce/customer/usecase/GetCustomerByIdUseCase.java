package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.mapper.CustomerDomainMapper;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCustomerByIdUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerDomainMapper customerDomainMapper;

    public CustomerResult execute(UUID id) {
        return customerRepository.findByIdWithCache(new CustomerId(id))
                .orElseThrow(() -> new CustomerDomainException("Customer not found"));
    }

}
