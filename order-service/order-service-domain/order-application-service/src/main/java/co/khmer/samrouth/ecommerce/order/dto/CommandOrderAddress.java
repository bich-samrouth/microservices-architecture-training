package co.khmer.samrouth.ecommerce.order.dto;

public record CommandOrderAddress(
        String street,
        String postalCode,
        String city
) {
}
