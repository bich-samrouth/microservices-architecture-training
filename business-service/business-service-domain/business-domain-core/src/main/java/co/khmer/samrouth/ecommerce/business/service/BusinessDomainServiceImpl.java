package co.khmer.samrouth.ecommerce.business.service;

import co.khmer.samrouth.ecommerce.business.entity.Business;
import co.khmer.samrouth.ecommerce.business.event.OrderApprovalEvent;
import co.khmer.samrouth.ecommerce.business.event.OrderApprovedEvent;
import co.khmer.samrouth.ecommerce.business.event.OrderRejectedEvent;
import co.khmer.samrouth.ecommerce.order.valueobject.OrderApprovalStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class BusinessDomainServiceImpl implements BusinessDomainService {

    @Override
    public OrderApprovalEvent validateOrder(Business business, List<String> failureMessages) {
        business.validateOrder(failureMessages);

        if (failureMessages.isEmpty()) {
            business.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(business.getOrderApproval(), business.getId(),
                    failureMessages, ZonedDateTime.now(ZoneId.of("UTC")));
        }

        business.constructOrderApproval(OrderApprovalStatus.REJECTED);
        return new OrderRejectedEvent(business.getOrderApproval(), business.getId(),
                failureMessages, ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
