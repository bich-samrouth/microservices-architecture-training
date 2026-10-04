package co.khmer.samrouth.ecommerce.payment.usecase;

import co.khmer.samrouth.ecommerce.payment.dto.PaymentResponseDto;
import co.khmer.samrouth.ecommerce.payment.mapper.PaymentDomainMapper;
import co.khmer.samrouth.ecommerce.payment.port.output.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllPaymentsUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentDomainMapper paymentDomainMapper;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> execute() {
        return paymentDomainMapper.paymentListToPaymentResponseDtoList(paymentRepository.findAll());
    }
}
