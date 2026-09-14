package co.khmer.samrouth.dto;


import co.khmer.samrouth.valueobject.OrderId;

public record CreateOrderResponse(
        OrderId orderId
) {
}
