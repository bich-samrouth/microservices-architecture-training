package co.khmer.samrouth.ecommerce.payment.usecase;

import co.khmer.samrouth.ecommerce.payment.dto.PaymentResponseDto;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentNotFoundException;
import co.khmer.samrouth.ecommerce.payment.mapper.PaymentDomainMapper;
import co.khmer.samrouth.ecommerce.payment.port.output.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindPaymentByOrderIdUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentDomainMapper paymentDomainMapper;

    @Transactional(readOnly = true)
    public PaymentResponseDto execute(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(paymentDomainMapper::paymentToPaymentResponseDto)
                .orElseThrow(() -> new PaymentNotFoundException("Could not find payment for order ID: " + orderId));
    }
}
