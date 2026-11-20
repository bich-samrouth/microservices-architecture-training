package co.khmer.samrouth.ecommerce.customer.restapi.controller;

import co.khmer.samrouth.ecommerce.core.dto.PageResponse;
import co.khmer.samrouth.ecommerce.customer.client.dto.UserResponse;
import co.khmer.samrouth.ecommerce.customer.client.feign.UserFeignClient;
import co.khmer.samrouth.ecommerce.customer.dto.CustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.GetCustomersQuery;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.mapper.CustomerWebMapper;
import co.khmer.samrouth.ecommerce.customer.usecase.GetAllCustomersUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.GetCustomerByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final UserFeignClient userFeignClient;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final CustomerWebMapper customerWebMapper;


    @GetMapping("/{id}")
    public CustomerResponse getByIdWithCache(@PathVariable UUID id) {
        CustomerResult result = getCustomerByIdUseCase.execute(id);
        return customerWebMapper.toCustomerResponse(result);
    }


    @GetMapping
    public PageResponse<CustomerResponse> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        PageResponse<CustomerResult> result = getAllCustomersUseCase.execute(new GetCustomersQuery(page, size));
        return customerWebMapper.toCustomerResponse(result);
    }


    @GetMapping("/check-user/{userId}")
    public UserResponse checkUser(@PathVariable String userId) {
        return userFeignClient.getById(userId);
    }

}
