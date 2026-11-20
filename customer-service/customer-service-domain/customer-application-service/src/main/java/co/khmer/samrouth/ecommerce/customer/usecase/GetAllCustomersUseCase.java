package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.GetCustomersQuery;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class GetAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    public PageResponse<CustomerResult> execute(GetCustomersQuery query) {
        log.info("Executing GetAllCustomersUseCase: {}", query);
        return customerRepository.findAll(query.page(), query.size());
    }
}
