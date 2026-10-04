package co.khmer.samrouth.ecommerce.customer.entity;

import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.order.entity.AggregateRoot;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;

import java.util.UUID;

public class Customer extends AggregateRoot<CustomerId> {
    private String username;
    private String familyName;
    private String givenName;
    private Email email;
    private PhoneNumber phoneNumber;
    private LoyaltyTier loyaltyTier;
    private CustomerStatus status;

    private Customer(Builder builder) {
        super.setId(builder.id);
        this.username = builder.username;
        this.familyName = builder.familyName;
        this.givenName = builder.givenName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.loyaltyTier = builder.loyaltyTier;
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeCustomer() {
        if (getId() == null) {
            setId(new CustomerId(UUID.randomUUID()));
        }
        this.status = CustomerStatus.ACTIVE;
        if (this.loyaltyTier == null) {
            this.loyaltyTier = LoyaltyTier.BRONZE;
        }
    }

    public void validateCustomer() {
        validateInitialCustomer();
    }

    private void validateInitialCustomer() {
        if (getId() == null) {
            throw new CustomerDomainException("Customer id is not initialized!");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new CustomerDomainException("Customer username cannot be empty!");
        }
        if (email == null || email.value() == null || email.value().trim().isEmpty()) {
            throw new CustomerDomainException("Customer email cannot be empty!");
        }
    }

    public void updateDetails(String familyName, String givenName, Email email, PhoneNumber phoneNumber) {
        if (email == null || email.value() == null || email.value().trim().isEmpty()) {
            throw new CustomerDomainException("Customer email cannot be empty!");
        }
        this.familyName = familyName;
        this.givenName = givenName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateLoyaltyTier(LoyaltyTier loyaltyTier) {
        if (loyaltyTier == null) {
            throw new CustomerDomainException("Loyalty tier cannot be null!");
        }
        this.loyaltyTier = loyaltyTier;
    }

    public void deactivate() {
        if (this.status == CustomerStatus.INACTIVE) {
            throw new CustomerDomainException("Customer is already inactive!");
        }
        this.status = CustomerStatus.INACTIVE;
    }

    public void activate() {
        if (this.status == CustomerStatus.ACTIVE) {
            throw new CustomerDomainException("Customer is already active!");
        }
        this.status = CustomerStatus.ACTIVE;
    }

    public String getUsername() {
        return username;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public LoyaltyTier getLoyaltyTier() {
        return loyaltyTier;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public static final class Builder {
        private CustomerId id;
        private String username;
        private String familyName;
        private String givenName;
        private Email email;
        private PhoneNumber phoneNumber;
        private LoyaltyTier loyaltyTier;
        private CustomerStatus status;

        private Builder() {
        }

        public Builder id(CustomerId val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder familyName(String val) {
            familyName = val;
            return this;
        }

        public Builder givenName(String val) {
            givenName = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder phoneNumber(PhoneNumber val) {
            phoneNumber = val;
            return this;
        }

        public Builder loyaltyTier(LoyaltyTier val) {
            loyaltyTier = val;
            return this;
        }

        public Builder status(CustomerStatus val) {
            status = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
