package co.khmer.samrouth.ecommerce.payment.usecase;

import co.khmer.samrouth.ecommerce.order.valueobject.CreditEntryId;
import co.khmer.samrouth.ecommerce.order.valueobject.CreditHistoryId;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.Money;
import co.khmer.samrouth.ecommerce.order.valueobject.TransactionType;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentResult;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.event.PaymentEvent;
import co.khmer.samrouth.ecommerce.payment.mapper.PaymentDomainMapper;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditEntryRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.CreditHistoryRepository;
import co.khmer.samrouth.ecommerce.payment.port.output.PaymentRepository;
import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessPaymentUseCase {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDomainMapper paymentDomainMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    @Transactional
    public CreatePaymentResult execute(CreatePaymentCommand command) {
        log.info("Processing payment for order id: {}", command.orderId());
        Payment payment = paymentDomainMapper.createPaymentCommandToPayment(command);

        // Check if payment already exists for this order
        Optional<Payment> existingPayment = paymentRepository.findByOrderId(command.orderId());
        if (existingPayment.isPresent()) {
            Payment p = existingPayment.get();
            log.warn("Payment already exists for order id: {}, status: {}", command.orderId(), p.getPaymentStatus());
            return CreatePaymentResult.builder()
                    .paymentId(p.getId().value())
                    .orderId(p.getOrderId().value())
                    .customerId(p.getCustomerId().value())
                    .price(p.getPrice().amount())
                    .paymentStatus(p.getPaymentStatus())
                    .createdAt(p.getCreatedAt())
                    .failureMessages(List.of())
                    .build();
        }

        CustomerId customerId = payment.getCustomerId();
        CreditEntry creditEntry = creditEntryRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    // Initialize a default credit entry for customer (1000.00 initial credit)
                    CreditEntry newEntry = CreditEntry.builder()
                            .id(new CreditEntryId(UUID.randomUUID()))
                            .customerId(customerId)
                            .totalCreditAmount(new Money(new BigDecimal("1000.00")))
                            .build();
                    return creditEntryRepository.save(newEntry);
                });

        List<CreditHistory> creditHistories = new ArrayList<>(creditHistoryRepository.findByCustomerId(customerId));
        if (creditHistories.isEmpty() && creditEntry.getTotalCreditAmount().isGreaterThanZero()) {
            CreditHistory initialCreditHistory = CreditHistory.builder()
                    .id(new CreditHistoryId(UUID.randomUUID()))
                    .customerId(customerId)
                    .amount(creditEntry.getTotalCreditAmount())
                    .transactionType(TransactionType.CREDIT)
                    .build();
            creditHistoryRepository.save(initialCreditHistory);
            creditHistories.add(initialCreditHistory);
        }

        List<String> failureMessages = new ArrayList<>();
        int historiesSizeBefore = creditHistories.size();

        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(
                payment, creditEntry, creditHistories, failureMessages
        );

        Payment savedPayment = paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            if (creditHistories.size() > historiesSizeBefore) {
                CreditHistory newHistory = creditHistories.get(creditHistories.size() - 1);
                creditHistoryRepository.save(newHistory);
            }
            log.info("Payment completed successfully with id: {}", savedPayment.getId().value());
        } else {
            log.warn("Payment failed for order id: {} with messages: {}", command.orderId(), failureMessages);
        }

        return CreatePaymentResult.builder()
                .paymentId(savedPayment.getId().value())
                .orderId(savedPayment.getOrderId().value())
                .customerId(savedPayment.getCustomerId().value())
                .price(savedPayment.getPrice().amount())
                .paymentStatus(savedPayment.getPaymentStatus())
                .createdAt(savedPayment.getCreatedAt())
                .failureMessages(failureMessages)
                .build();
    }
}
