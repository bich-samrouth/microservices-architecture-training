package co.khmer.samrouth.dto;

import co.khmer.samrouth.valueobject.BusinessId;
import co.khmer.samrouth.valueobject.CustomerId;
import co.khmer.samrouth.valueobject.Money;
import co.khmer.samrouth.valueobject.StreetAddress;

public record CreateOrderRequest(
        CustomerId customerId,
        BusinessId businessId,
        StreetAddress deliveryAddress,
        Money price
) {
}
