package co.khmer.samrouth.ecommerce.customer.dto;

public record CreateCustomerCommand(
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber) {
}
