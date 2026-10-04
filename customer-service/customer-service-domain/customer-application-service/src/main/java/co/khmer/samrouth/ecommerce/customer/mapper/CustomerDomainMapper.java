package co.khmer.samrouth.ecommerce.customer.mapper;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerDomainMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loyaltyTier", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "phoneNumber", target = "phoneNumber.number")
    Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommand);
}
