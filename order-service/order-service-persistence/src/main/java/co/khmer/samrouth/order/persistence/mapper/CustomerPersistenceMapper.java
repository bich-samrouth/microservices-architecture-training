package co.khmer.samrouth.order.persistence.mapper;

import co.khmer.samrouth.domain.entity.Customer;
import co.khmer.samrouth.order.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {
    // map customer entity to customer
    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
}
