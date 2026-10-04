package co.khmer.samrouth.ecommerce.payment.usecase;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentResult;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.exception.CreditEntryNotFoundException;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentNotFoundException;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditEntryRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditHistoryRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.PaymentRepository;
import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelPaymentUseCase {

    private final PaymentDomainService paymentDomainService;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    @Transactional
    public CancelPaymentResult execute(CancelPaymentCommand command) {
        log.info("Cancelling payment with id: {}", command.paymentId());
        Payment payment = paymentRepository.findById(command.paymentId())
                .orElseThrow(() -> new PaymentNotFoundException("Could not find payment with ID: " + command.paymentId()));

        CustomerId customerId = payment.getCustomerId();
        CreditEntry creditEntry = creditEntryRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CreditEntryNotFoundException("Could not find credit entry for customer: " + customerId.value()));

        List<CreditHistory> creditHistories = new ArrayList<>(creditHistoryRepository.findByCustomerId(customerId));
        List<String> failureMessages = new ArrayList<>();
        int historiesSizeBefore = creditHistories.size();

        paymentDomainService.cancelPayment(payment, creditEntry, creditHistories, failureMessages);

        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            if (creditHistories.size() > historiesSizeBefore) {
                CreditHistory newHistory = creditHistories.get(creditHistories.size() - 1);
                creditHistoryRepository.save(newHistory);
            }
            log.info("Payment cancelled successfully with id: {}", payment.getId().value());
        } else {
            log.warn("Payment cancellation failed for payment id: {} with messages: {}", command.paymentId(), failureMessages);
        }

        return CancelPaymentResult.builder()
                .paymentId(payment.getId().value())
                .paymentStatus(payment.getPaymentStatus())
                .failureMessages(failureMessages)
                .build();
    }
}
