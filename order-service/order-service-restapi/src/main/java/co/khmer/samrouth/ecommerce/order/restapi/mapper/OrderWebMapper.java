package co.khmer.samrouth.ecommerce.order.restapi.mapper;

import co.khmer.samrouth.ecommerce.order.dto.CreateOrderCommand;
import co.khmer.samrouth.ecommerce.order.dto.CreateOrderResult;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateRequest;
import co.khmer.samrouth.ecommerce.order.restapi.dto.OrderCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
    // Source = OrderCreateRequest
    // Target = CreateOrderCommand
    @Mapping(source = "orderAddress",target = "deliveryAddress")
    CreateOrderCommand orderCreateRequestToCreateOrderCommand(OrderCreateRequest orderCreateRequest);

    OrderCreateResponse createOrderResultToOrderCreateResponse(CreateOrderResult createOrderResult);
}
