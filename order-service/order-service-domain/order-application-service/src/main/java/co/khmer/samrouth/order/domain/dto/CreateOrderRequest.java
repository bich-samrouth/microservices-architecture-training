package co.khmer.samrouth.order.domain.dto;

import co.khmer.samrouth.domain.valueobject.BusinessId;
import co.khmer.samrouth.domain.valueobject.CustomerId;
import co.khmer.samrouth.domain.valueobject.Money;
import co.khmer.samrouth.domain.valueobject.StreetAddress;

public record CreateOrderRequest(
        CustomerId customerId,
        BusinessId businessId,
        StreetAddress deliveryAddress,
        Money price
) {
}
