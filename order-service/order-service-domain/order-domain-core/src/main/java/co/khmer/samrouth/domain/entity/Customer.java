package co.khmer.samrouth.domain.entity;

import co.khmer.samrouth.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    private final String userName;
    private final String familyName;
    private final String givenName;

    private Customer(Builder builder) {
        super.setId(builder.id);
        userName = builder.userName;
        familyName = builder.familyName;
        givenName = builder.givenName;
    }

    public static Builder builder(){
        return new Builder();
    }

    public String getUserName() {
        return userName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }


    public static final class Builder {
        private CustomerId id;
        private String userName;
        private String familyName;
        private String givenName;

        private Builder() {
        }

        public Builder id(CustomerId val) {
            id = val;
            return this;
        }

        public Builder userName(String val) {
            userName = val;
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

        public Customer build() {
            return new Customer(this);
        }
    }
}