package co.khmer.samrouth.ecommerce.customer.restapi.controller;

import co.khmer.samrouth.ecommerce.customer.client.dto.UserResponse;
import co.khmer.samrouth.ecommerce.customer.client.feign.UserFeignClient;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerQueryController {
    private final UserFeignClient userFeignClient;

    @GetMapping("/check-user/{userId}")
    public UserResponse checkUser(@PathVariable String userId){
        return userFeignClient.getById(userId);
    }

    @GetMapping

    public CustomerResponse getAllCustomers(
            @RequestParam(required = false , defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ){
        return null;
    }

}
