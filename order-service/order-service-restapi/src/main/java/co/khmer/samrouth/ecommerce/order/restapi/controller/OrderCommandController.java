package co.khmer.samrouth.ecommerce.order.restapi.controller;

import co.khmer.samrouth.ecommerce.order.dto.CreateOrderCommand;
import co.khmer.samrouth.ecommerce.order.dto.CreateOrderResult;
import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.restapi.dto.CancelOrderRequest;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateRequest;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateResponse;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderResponse;
import co.khmer.samrouth.ecommerce.order.restapi.mapper.OrderWebMapper;
import co.khmer.samrouth.ecommerce.order.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FindOrderByTrackingIdUseCase findOrderByTrackingIdUseCase;
    private final FindAllOrdersUseCase findAllOrdersUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    // Create order
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        // Mapping logic
        CreateOrderCommand createOrderCommand = orderWebMapper
                .orderCreateRequestToCreateOrderCommand(orderCreateRequest);
        // UseCase logic
        CreateOrderResult createOrderResult = createOrderUseCase.execute(createOrderCommand);

        return orderWebMapper.createOrderResultToOrderCreateResponse(createOrderResult);
    }

    // Cancel order
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{orderId}/cancel")
    public void cancelOrder(
            @PathVariable UUID orderId,
            @RequestBody(required = false) CancelOrderRequest cancelOrderRequest
    ) {
        cancelOrderUseCase.execute(
                orderId,
                cancelOrderRequest != null && cancelOrderRequest.failureMessages() != null
                        ? cancelOrderRequest.failureMessages()
                        : Collections.emptyList()
        );
    }

    // Delete order
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable UUID orderId) {
        deleteOrderUseCase.execute(orderId);
    }

    // Get order by ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId) {
        Order order = findOrderByIdUseCase.execute(orderId);
        return orderWebMapper.orderToOrderResponse(order);
    }

    // Get order by tracking ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tracking/{trackingId}")
    public OrderResponse getOrderByTrackingId(@PathVariable UUID trackingId) {
        Order order = findOrderByTrackingIdUseCase.execute(trackingId);
        return orderWebMapper.orderToOrderResponse(order);
    }

    // Get all orders or filter by customer ID
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrderResponse> getOrders(@RequestParam(required = false) UUID customerId) {
        List<Order> orders = findAllOrdersUseCase.execute(customerId);
        return orderWebMapper.orderListToOrderResponseList(orders);
    }
}
