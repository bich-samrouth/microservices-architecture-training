package co.khmer.samrouth.ecommerce.customer.restapi.mapper;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CreateCustomerCommand customerCreateRequestToCreateCustomerCommand(CustomerCreateRequest request);

    CustomerCreateResponse createCustomerResultToCustomerCreateResponse(CreateCustomerResult result);

    @Mapping(target = "customerId", source = "customerId")
    UpdateCustomerCommand customerUpdateRequestToUpdateCustomerCommand(UUID customerId, CustomerUpdateRequest request);

    CustomerUpdateResponse updateCustomerResultToCustomerUpdateResponse(UpdateCustomerResult result);

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "phoneNumber.number", target = "phoneNumber")
    CustomerResponse customerToCustomerResponse(Customer customer);

    List<CustomerResponse> customerListToCustomerResponseList(List<Customer> customers);
}
