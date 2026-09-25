package co.khmer.samrouth.order.persistence.entity;

import co.khmer.samrouth.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

// JPA Entity must be POJO (Plain old Java Object)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;

    private UUID customerId;

    private UUID businessId;

    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddressEntity orderAddress;

    private UUID trackingId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String failureMessages;
}
