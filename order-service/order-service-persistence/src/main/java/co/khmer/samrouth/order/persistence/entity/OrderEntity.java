package co.khmer.samrouth.order.persistence.entity;

import co.khmer.samrouth.valueobject.OrderStatus;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID customerId;
    private UUID businessId;
    private BigDecimal price;
    private UUID trackingId;
    private OrderStatus orderStatus;
    private String failureMessage;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems;

    @OneToOne(mappedBy = "order")
    private OrderAddressEntity orderAddress;
}
