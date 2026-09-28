package co.khmer.samrouth.ecommerce.payment.service;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.valueobject.CreditHistoryId;
import co.khmer.samrouth.ecommerce.order.valueobject.Money;
import co.khmer.samrouth.ecommerce.order.valueobject.TransactionType;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCancelledEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCompletedEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentFailedEvent;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentDomainException;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentDomainServiceImp implements PaymentDomainService{
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public PaymentEvent validateAndInitiatePayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages
    ) {
        // 1. Validate the payment itself (collect the error , don't stop)
        try {
            payment.validatePayment();
        }catch (PaymentDomainException e){
            failureMessages.add(e.getMessage());
        }
        // 2. Always move it to PENDING so it can be completed or failed
        payment.initializePayment();

        // 3. Business rules involving credit
        if(failureMessages.isEmpty()){
            validateCreditEntry(payment, creditEntry, failureMessages);
        }
        if (failureMessages.isEmpty()) {
            creditEntry.subtractCreditAmount(payment.getPrice());
            creditHistories.add(newCreditHistory(payment, TransactionType.DEBIT));
            validateCreditHistory(creditEntry, creditHistories, failureMessages);
        }

        // 4. Decide the outcome
        if (failureMessages.isEmpty()) {
            payment.complete();
            return new PaymentCompletedEvent(payment, now(), failureMessages);
        }
        payment.fail();

        return new PaymentFailedEvent(payment, now(), failureMessages);
    }

    @Override
    public PaymentEvent cancelPayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages) {

        // 1. Validate Credit History
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        // 2. Credit data is inconsistent: don't refund, report failure
        if (!failureMessages.isEmpty()) {
            return new PaymentFailedEvent(payment, now(), failureMessages);
        }

        payment.cancel();
        creditEntry.addCreditAmount(payment.getPrice()); // refund
        creditHistories.add(newCreditHistory(payment, TransactionType.CREDIT));
        return new PaymentCancelledEvent(payment, now(), failureMessages);
    }

    @Override
    public PaymentEvent failPayment(Payment payment, List<String> failureMessages) {
        payment.fail();
        return new PaymentFailedEvent(payment, now(), failureMessages);
    }


    private void validateCreditEntry(Payment payment,
                                    CreditEntry creditEntry,
                                    List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            failureMessages.add("Customer with id " + payment.getCustomerId()
                    + " doesn't have enough credit for payment");
        }
    }

    // Ledger check: total CREDIT minus total DEBIT must equal the current credit
    private void validateCreditHistory(CreditEntry creditEntry,
                                       List<CreditHistory> creditHistories,
                                       List<String> failureMessages) {
        Money totalCredit = sumOf(creditHistories, TransactionType.CREDIT);
        Money totalDebit  = sumOf(creditHistories, TransactionType.DEBIT);

        if (totalDebit.isGreaterThan(totalCredit)) {
            failureMessages.add("Customer with id " + creditEntry.getCustomerId()
                    + " doesn't have enough credit according to credit history");
        }
        if (!creditEntry.getTotalCreditAmount().equals(totalCredit.subtract(totalDebit))) {
            failureMessages.add("Credit history total is not equal to current credit for customer id "
                    + creditEntry.getCustomerId());
        }
    }

    private Money sumOf(List<CreditHistory> histories, TransactionType type) {
        return histories.stream()
                .filter(h -> h.getTransactionType() == type)
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private CreditHistory newCreditHistory(Payment payment, TransactionType type) {
        return CreditHistory.builder()
                .id(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(type)
                .build();
    }

    private ZonedDateTime now() {
        return ZonedDateTime.now(UTC);
    }
}
