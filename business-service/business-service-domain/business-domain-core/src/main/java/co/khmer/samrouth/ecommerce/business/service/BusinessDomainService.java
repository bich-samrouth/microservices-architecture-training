package co.khmer.samrouth.ecommerce.business.service;

import co.khmer.samrouth.ecommerce.business.entity.Business;
import co.khmer.samrouth.ecommerce.business.event.OrderApprovalEvent;

import java.util.List;

public interface BusinessDomainService {
    OrderApprovalEvent validateOrder(Business business, List<String> failureMessages);
}

