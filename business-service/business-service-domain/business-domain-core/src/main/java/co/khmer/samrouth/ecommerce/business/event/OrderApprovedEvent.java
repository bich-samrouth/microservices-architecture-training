package co.khmer.samrouth.ecommerce.business.event;

import co.khmer.samrouth.ecommerce.business.entity.OrderApproval;
import co.khmer.samrouth.ecommerce.order.valueobject.BusinessId;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderApprovedEvent extends OrderApprovalEvent {
    public OrderApprovedEvent(OrderApproval orderApproval, BusinessId businessId, List<String> failureMessages, ZonedDateTime createdAt) {
        super(orderApproval, businessId, failureMessages, createdAt);
    }
}
