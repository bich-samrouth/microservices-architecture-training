package co.khmer.samrouth.ecommerce.order.restapi.controller;

import co.khmer.samrouth.ecommerce.order.dto.CreateOrderCommand;
import co.khmer.samrouth.ecommerce.order.dto.CreateOrderResult;
import co.khmer.samrouth.ecommerce.order.usecase.CreateOrderUseCase;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateRequest;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateResponse;
import co.khmer.samrouth.ecommerce.order.restapi.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    // Declare required dependency
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    // Create order
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(@Valid @RequestBody  OrderCreateRequest orderCreateRequest){
        // Mapping logic
        CreateOrderCommand createOrderCommand = orderWebMapper
                .orderCreateRequestToCreateOrderCommand(orderCreateRequest);
        // UseCase logic
        CreateOrderResult createOrderResult = createOrderUseCase.execute(createOrderCommand);

        return orderWebMapper.createOrderResultToOrderCreateResponse(createOrderResult);
    }

}
