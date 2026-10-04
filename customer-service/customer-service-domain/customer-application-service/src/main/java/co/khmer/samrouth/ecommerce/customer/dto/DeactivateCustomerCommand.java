package co.khmer.samrouth.ecommerce.customer.dto;

import java.util.UUID;

public record DeactivateCustomerCommand(
        UUID customerId
) {
}