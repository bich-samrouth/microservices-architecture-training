package co.khmer.samrouth.ecommerce.payment.port.output;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;

import java.util.List;

public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory);
    List<CreditHistory> findByCustomerId(CustomerId customerId);
}
