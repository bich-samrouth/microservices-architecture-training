package co.khmer.samrouth.ecommerce.order.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class OrderItemIdEntity implements Serializable {
    private Integer id;
    private OrderEntity order;
}
