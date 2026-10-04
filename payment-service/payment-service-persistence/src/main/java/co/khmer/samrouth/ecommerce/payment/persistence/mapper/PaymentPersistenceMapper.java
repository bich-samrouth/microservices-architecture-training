package co.khmer.samrouth.ecommerce.payment.persistence.mapper;

import co.khmer.samrouth.ecommerce.order.valueobject.*;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditEntryEntity;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.CreditHistoryEntity;
import co.khmer.samrouth.ecommerce.payment.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentPersistenceMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        if (payment == null) return null;
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId() != null ? payment.getId().value() : null);
        entity.setOrderId(payment.getOrderId() != null ? payment.getOrderId().value() : null);
        entity.setCustomerId(payment.getCustomerId() != null ? payment.getCustomerId().value() : null);
        entity.setPrice(payment.getPrice() != null ? payment.getPrice().amount() : null);
        entity.setPaymentStatus(payment.getPaymentStatus());
        entity.setCreatedAt(payment.getCreatedAt());
        entity.setUpdatedAt(payment.getUpdatedAt());
        return entity;
    }

    public Payment paymentEntityToPayment(PaymentEntity entity) {
        if (entity == null) return null;
        return Payment.builder()
                .id(entity.getId() != null ? new PaymentId(entity.getId()) : null)
                .orderId(entity.getOrderId() != null ? new OrderId(entity.getOrderId()) : null)
                .customerId(entity.getCustomerId() != null ? new CustomerId(entity.getCustomerId()) : null)
                .price(entity.getPrice() != null ? new Money(entity.getPrice()) : null)
                .paymentStatus(entity.getPaymentStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<Payment> paymentEntityListToPaymentList(List<PaymentEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::paymentEntityToPayment).toList();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        if (creditEntry == null) return null;
        CreditEntryEntity entity = new CreditEntryEntity();
        entity.setId(creditEntry.getId() != null ? creditEntry.getId().value() : null);
        entity.setCustomerId(creditEntry.getCustomerId() != null ? creditEntry.getCustomerId().value() : null);
        entity.setTotalCreditAmount(creditEntry.getTotalCreditAmount() != null ? creditEntry.getTotalCreditAmount().amount() : null);
        return entity;
    }

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity entity) {
        if (entity == null) return null;
        return CreditEntry.builder()
                .id(entity.getId() != null ? new CreditEntryId(entity.getId()) : null)
                .customerId(entity.getCustomerId() != null ? new CustomerId(entity.getCustomerId()) : null)
                .totalCreditAmount(entity.getTotalCreditAmount() != null ? new Money(entity.getTotalCreditAmount()) : null)
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        if (creditHistory == null) return null;
        CreditHistoryEntity entity = new CreditHistoryEntity();
        entity.setId(creditHistory.getId() != null ? creditHistory.getId().value() : null);
        entity.setCustomerId(creditHistory.getCustomerId() != null ? creditHistory.getCustomerId().value() : null);
        entity.setAmount(creditHistory.getAmount() != null ? creditHistory.getAmount().amount() : null);
        entity.setTransactionType(creditHistory.getTransactionType());
        return entity;
    }

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity entity) {
        if (entity == null) return null;
        return CreditHistory.builder()
                .id(entity.getId() != null ? new CreditHistoryId(entity.getId()) : null)
                .customerId(entity.getCustomerId() != null ? new CustomerId(entity.getCustomerId()) : null)
                .amount(entity.getAmount() != null ? new Money(entity.getAmount()) : null)
                .transactionType(entity.getTransactionType())
                .build();
    }

    public List<CreditHistory> creditHistoryEntityListToCreditHistoryList(List<CreditHistoryEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::creditHistoryEntityToCreditHistory).toList();
    }
}
