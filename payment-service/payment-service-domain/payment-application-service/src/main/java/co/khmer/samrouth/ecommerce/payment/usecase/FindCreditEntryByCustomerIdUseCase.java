package co.khmer.samrouth.ecommerce.payment.usecase;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.dto.CreditEntryDto;
import co.khmer.samrouth.ecommerce.payment.exception.CreditEntryNotFoundException;
import co.khmer.samrouth.ecommerce.payment.mapper.PaymentDomainMapper;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindCreditEntryByCustomerIdUseCase {

    private final CreditEntryRepository creditEntryRepository;
    private final PaymentDomainMapper paymentDomainMapper;

    @Transactional(readOnly = true)
    public CreditEntryDto execute(UUID customerId) {
        return creditEntryRepository.findByCustomerId(new CustomerId(customerId))
                .map(paymentDomainMapper::creditEntryToCreditEntryDto)
                .orElseThrow(() -> new CreditEntryNotFoundException("Could not find credit entry for customer: " + customerId));
    }
}
