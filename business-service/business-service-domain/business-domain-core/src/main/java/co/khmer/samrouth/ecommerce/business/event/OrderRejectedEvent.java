package co.khmer.samrouth.ecommerce.business.event;

import co.khmer.samrouth.ecommerce.business.entity.OrderApproval;
import co.khmer.samrouth.ecommerce.order.valueobject.BusinessId;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderRejectedEvent extends OrderApprovalEvent {
    public OrderRejectedEvent(OrderApproval orderApproval, BusinessId businessId, List<String> failureMessages, ZonedDateTime createdAt) {
        super(orderApproval, businessId, failureMessages, createdAt);
    }
}
