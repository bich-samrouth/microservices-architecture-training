package co.khmer.samrouth.ecommerce.business.port.output;

import co.khmer.samrouth.ecommerce.business.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}

