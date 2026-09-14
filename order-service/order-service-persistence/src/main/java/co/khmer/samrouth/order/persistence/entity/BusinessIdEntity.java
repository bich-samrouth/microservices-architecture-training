package co.khmer.samrouth.order.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class BusinessIdEntity implements Serializable {
    private UUID businessId;
    private UUID productId;
}
