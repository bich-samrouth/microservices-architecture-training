package co.khmer.samrouth.ecommerce.order.restapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CancelOrderRequest(
        List<String> failureMessages
) {
}
