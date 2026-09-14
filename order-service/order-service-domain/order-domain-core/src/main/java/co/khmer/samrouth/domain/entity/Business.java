package co.khmer.samrouth.domain.entity;

import co.khmer.samrouth.domain.valueobject.BusinessId;

import java.util.List;

public class Business extends AggregateRoot<BusinessId> {
    private final List<Product> products;
    private final boolean active;
    private final String userName;
    private final String familyName;
    private final String givenName;

    private Business(Builder builder) {
        super.setId(builder.id);
        products = builder.products;
        active = builder.active;
        userName = builder.userName;
        familyName = builder.familyName;
        givenName = builder.givenName;
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

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }


    public static final class Builder {
        private BusinessId id;
        private List<Product> products;
        private boolean active;
        private String userName;
        private String familyName;
        private String givenName;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(BusinessId val) {
            id = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
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

        public Business build() {
            return new Business(this);
        }
    }
}
