package co.khmer.samrouth.ecommerce.customer.dto;

public record CreateCustomerCommand(
        String userId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber) {
}
