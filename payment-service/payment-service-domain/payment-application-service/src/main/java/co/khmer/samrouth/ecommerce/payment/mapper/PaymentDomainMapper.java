package co.khmer.samrouth.ecommerce.payment.mapper;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.Money;
import co.khmer.samrouth.ecommerce.order.valueobject.OrderId;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CreditEntryDto;
import co.khmer.samrouth.ecommerce.payment.dto.CreditHistoryDto;
import co.khmer.samrouth.ecommerce.payment.dto.PaymentResponseDto;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentDomainMapper {

    public Payment createPaymentCommandToPayment(CreatePaymentCommand command) {
        return Payment.builder()
                .orderId(new OrderId(command.orderId()))
                .customerId(new CustomerId(command.customerId()))
                .price(new Money(command.price()))
                .build();
    }

    public PaymentResponseDto paymentToPaymentResponseDto(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId() != null ? payment.getId().value() : null)
                .orderId(payment.getOrderId() != null ? payment.getOrderId().value() : null)
                .customerId(payment.getCustomerId() != null ? payment.getCustomerId().value() : null)
                .price(payment.getPrice() != null ? payment.getPrice().amount() : null)
                .paymentStatus(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    public List<PaymentResponseDto> paymentListToPaymentResponseDtoList(List<Payment> payments) {
        return payments.stream().map(this::paymentToPaymentResponseDto).toList();
    }

    public CreditEntryDto creditEntryToCreditEntryDto(CreditEntry creditEntry) {
        return CreditEntryDto.builder()
                .id(creditEntry.getId() != null ? creditEntry.getId().value() : null)
                .customerId(creditEntry.getCustomerId() != null ? creditEntry.getCustomerId().value() : null)
                .totalCreditAmount(creditEntry.getTotalCreditAmount() != null ? creditEntry.getTotalCreditAmount().amount() : null)
                .build();
    }

    public CreditHistoryDto creditHistoryToCreditHistoryDto(CreditHistory creditHistory) {
        return CreditHistoryDto.builder()
                .id(creditHistory.getId() != null ? creditHistory.getId().value() : null)
                .customerId(creditHistory.getCustomerId() != null ? creditHistory.getCustomerId().value() : null)
                .amount(creditHistory.getAmount() != null ? creditHistory.getAmount().amount() : null)
                .transactionType(creditHistory.getTransactionType())
                .build();
    }

    public List<CreditHistoryDto> creditHistoryListToCreditHistoryDtoList(List<CreditHistory> creditHistories) {
        return creditHistories.stream().map(this::creditHistoryToCreditHistoryDto).toList();
    }
}
