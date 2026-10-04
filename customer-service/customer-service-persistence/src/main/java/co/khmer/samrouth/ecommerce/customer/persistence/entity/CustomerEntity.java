package co.khmer.samrouth.ecommerce.customer.persistence.entity;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private UUID id;

    @Column(unique = true)
    private String username;

    private String familyName;

    private String givenName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private LoyaltyTier loyaltyTier;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
}
