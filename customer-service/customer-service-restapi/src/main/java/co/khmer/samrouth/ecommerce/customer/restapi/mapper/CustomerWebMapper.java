package co.khmer.samrouth.ecommerce.customer.restapi.mapper;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.dto.*;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    CustomerResponse toCustomerResponse(CustomerResult customerResult);

    CreateCustomerCommand customerCreateRequestToCreateCustomerCommand(CustomerCreateRequest customerCreateRequest);

    CustomerCreateResponse createCustomerResultToCustomerCreateResponse(CreateCustomerResult createCustomerResult);

    // customerId comes from the path, the rest from the request body
    @Mapping(source = "customerId", target = "customerId")
    UpdateCustomerCommand customerUpdateRequestToUpdateCustomerCommand(UUID customerId, CustomerUpdateRequest customerUpdateRequest);

    CustomerUpdateResponse updateCustomerResultToCustomerUpdateResponse(UpdateCustomerResult updateCustomerResult);

    PageResponse<CustomerResponse> toCustomerResponse(PageResponse<CustomerResult> result);
}
