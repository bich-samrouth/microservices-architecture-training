package co.khmer.samrouth.ecommerce.order.restapi.mapper;

import co.khmer.samrouth.ecommerce.order.dto.CreateOrderCommand;
import co.khmer.samrouth.ecommerce.order.dto.CreateOrderResult;
import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.entity.OrderItem;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderAddressResponse;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateRequest;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateResponse;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderItemResponse;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderResponse;
import co.khmer.samrouth.ecommerce.order.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
    // Source = OrderCreateRequest
    // Target = CreateOrderCommand
    @Mapping(source = "orderAddress", target = "deliveryAddress")
    CreateOrderCommand orderCreateRequestToCreateOrderCommand(OrderCreateRequest orderCreateRequest);

    OrderCreateResponse createOrderResultToOrderCreateResponse(CreateOrderResult createOrderResult);

    @Mapping(source = "id.value", target = "orderId")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "businessId.value", target = "businessId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "trackingId.value", target = "trackingId")
    @Mapping(source = "deliveryAddress", target = "deliveryAddress")
    @Mapping(source = "items", target = "items")
    OrderResponse orderToOrderResponse(Order order);

    List<OrderResponse> orderListToOrderResponseList(List<Order> orders);

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "product.id.value", target = "productId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "subTotal.amount", target = "subTotal")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);

    OrderAddressResponse streetAddressToOrderAddressResponse(StreetAddress streetAddress);
}
