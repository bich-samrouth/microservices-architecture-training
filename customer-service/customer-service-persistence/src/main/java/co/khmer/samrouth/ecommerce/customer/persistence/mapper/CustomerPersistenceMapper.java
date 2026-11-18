package co.khmer.samrouth.ecommerce.customer.persistence.mapper;

import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "phoneNumber.number", target = "phoneNumber")
    CustomerEntity customerToCustomerEntity(Customer customer);

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "phoneNumber", target = "phoneNumber.number")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    CustomerResult toCustomerResult(CustomerEntity customerEntity);
}
