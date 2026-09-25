package co.khmer.samrouth.order.persistence.mapper;

import co.khmer.samrouth.domain.entity.Business;
import co.khmer.samrouth.domain.entity.Product;
import co.khmer.samrouth.domain.valueobject.BusinessId;
import co.khmer.samrouth.domain.valueobject.Money;
import co.khmer.samrouth.domain.valueobject.ProductId;
import co.khmer.samrouth.order.persistence.entity.BusinessEntity;
import co.khmer.samrouth.persistence.business.exception.BusinessPersistenceException;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    default List<UUID> businessToBusinessProducts(Business business) {
        return business.getProducts().stream()
                .map(product -> product.getId().value())
                .toList();
    }

    default Business businessEntityToBusiness(List<BusinessEntity> businessEntities) {
        BusinessEntity businessEntity = businessEntities.stream()
                .findFirst()
                .orElseThrow(() -> new BusinessPersistenceException("Business could not be found"));

        List<Product> businessProducts = businessEntities.stream()
                .map(entity -> Product.builder()
                        .id(new ProductId(entity.getProductId()))
                        .name(entity.getProductName())
                        .price(new Money(entity.getProductPrice()))
                        .build())
                .toList();

        return Business.builder()
                .id(new BusinessId(businessEntity.getBusinessId()))
                .products(businessProducts)
                .active(businessEntity.getBusinessActive())
                .build();
    }
}
