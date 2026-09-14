package co.khmer.samrouth.order.domain.dto;


import co.khmer.samrouth.domain.valueobject.OrderId;

public record CreateOrderResponse(
        OrderId orderId
) {
}
