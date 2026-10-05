package co.khmer.samrouth.ecommerce.business.event;

import co.khmer.samrouth.ecommerce.business.entity.OrderApproval;
import co.khmer.samrouth.ecommerce.order.event.DomainEvent;
import co.khmer.samrouth.ecommerce.order.valueobject.BusinessId;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class OrderApprovalEvent implements DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final BusinessId businessId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;

    public OrderApprovalEvent(OrderApproval orderApproval, BusinessId businessId, List<String> failureMessages, ZonedDateTime createdAt) {
        this.orderApproval = orderApproval;
        this.businessId = businessId;
        this.failureMessages = failureMessages;
        this.createdAt = createdAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public BusinessId getBusinessId() {
        return businessId;
    }

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }
}

