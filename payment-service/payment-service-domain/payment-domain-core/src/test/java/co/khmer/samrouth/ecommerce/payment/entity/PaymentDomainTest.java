package co.khmer.samrouth.ecommerce.payment.entity;

import co.khmer.samrouth.ecommerce.order.valueobject.*;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCancelledEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCompletedEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentFailedEvent;
import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainService;
import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDomainTest {

    private PaymentDomainService paymentDomainService;

    @BeforeEach
    void setUp() {
        paymentDomainService = new PaymentDomainServiceImp();
    }

    @Test
    void testValidateAndInitiatePayment_Success() {
        UUID customerUuid = UUID.randomUUID();
        CustomerId customerId = new CustomerId(customerUuid);
        Payment payment = Payment.builder()
                .orderId(new OrderId(UUID.randomUUID()))
                .customerId(customerId)
                .price(new Money(new BigDecimal("100.00")))
                .build();

        CreditEntry creditEntry = CreditEntry.builder()
                .id(new CreditEntryId(UUID.randomUUID()))
                .customerId(customerId)
                .totalCreditAmount(new Money(new BigDecimal("500.00")))
                .build();

        CreditHistory creditHistory = CreditHistory.builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(customerId)
                .amount(new Money(new BigDecimal("500.00")))
                .transactionType(TransactionType.CREDIT)
                .build();

        List<CreditHistory> creditHistories = new ArrayList<>(List.of(creditHistory));
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent event = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        assertTrue(event instanceof PaymentCompletedEvent);
        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
        assertEquals(new Money(new BigDecimal("400.00")), creditEntry.getTotalCreditAmount());
        assertEquals(2, creditHistories.size());
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void testValidateAndInitiatePayment_InsufficientCredit_Fails() {
        UUID customerUuid = UUID.randomUUID();
        CustomerId customerId = new CustomerId(customerUuid);
        Payment payment = Payment.builder()
                .orderId(new OrderId(UUID.randomUUID()))
                .customerId(customerId)
                .price(new Money(new BigDecimal("600.00")))
                .build();

        CreditEntry creditEntry = CreditEntry.builder()
                .id(new CreditEntryId(UUID.randomUUID()))
                .customerId(customerId)
                .totalCreditAmount(new Money(new BigDecimal("500.00")))
                .build();

        CreditHistory creditHistory = CreditHistory.builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(customerId)
                .amount(new Money(new BigDecimal("500.00")))
                .transactionType(TransactionType.CREDIT)
                .build();

        List<CreditHistory> creditHistories = new ArrayList<>(List.of(creditHistory));
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent event = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        assertTrue(event instanceof PaymentFailedEvent);
        assertEquals(PaymentStatus.FAILED, payment.getPaymentStatus());
        assertFalse(failureMessages.isEmpty());
    }

    @Test
    void testCancelPayment_Success() {
        UUID customerUuid = UUID.randomUUID();
        CustomerId customerId = new CustomerId(customerUuid);
        Payment payment = Payment.builder()
                .id(new PaymentId(UUID.randomUUID()))
                .orderId(new OrderId(UUID.randomUUID()))
                .customerId(customerId)
                .price(new Money(new BigDecimal("100.00")))
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        CreditEntry creditEntry = CreditEntry.builder()
                .id(new CreditEntryId(UUID.randomUUID()))
                .customerId(customerId)
                .totalCreditAmount(new Money(new BigDecimal("400.00")))
                .build();

        CreditHistory history1 = CreditHistory.builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(customerId)
                .amount(new Money(new BigDecimal("500.00")))
                .transactionType(TransactionType.CREDIT)
                .build();

        CreditHistory history2 = CreditHistory.builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(customerId)
                .amount(new Money(new BigDecimal("100.00")))
                .transactionType(TransactionType.DEBIT)
                .build();

        List<CreditHistory> creditHistories = new ArrayList<>(List.of(history1, history2));
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent event = paymentDomainService.cancelPayment(payment, creditEntry, creditHistories, failureMessages);

        assertTrue(event instanceof PaymentCancelledEvent);
        assertEquals(PaymentStatus.CANCELLED, payment.getPaymentStatus());
        assertEquals(new Money(new BigDecimal("500.00")), creditEntry.getTotalCreditAmount());
        assertEquals(3, creditHistories.size());
        assertTrue(failureMessages.isEmpty());
    }
}
