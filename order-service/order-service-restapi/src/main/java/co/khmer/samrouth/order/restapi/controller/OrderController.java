package co.khmer.samrouth.order.restapi.controller;

import co.khmer.samrouth.order.restapi.dto.OrderCreateRequest;
import co.khmer.samrouth.order.restapi.dto.OrderCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    // Create order
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(@Valid @RequestBody  OrderCreateRequest orderCreateRequest){
        return OrderCreateResponse.builder().orderId(UUID.randomUUID()).build();
    }

    // Get all orders

}
