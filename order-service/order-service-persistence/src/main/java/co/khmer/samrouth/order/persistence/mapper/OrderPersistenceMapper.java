package co.khmer.samrouth.order.persistence.mapper;

import co.khmer.samrouth.domain.entity.Business;
import co.khmer.samrouth.domain.entity.Customer;
import co.khmer.samrouth.order.persistence.entity.BusinessEntity;
import co.khmer.samrouth.order.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    // map customer entity to customer
    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    // map business entity to business
    @Mapping(source = "businessId", target = "id.value")
    Business businessEntityToBusiness(BusinessEntity businessEntity);
}
