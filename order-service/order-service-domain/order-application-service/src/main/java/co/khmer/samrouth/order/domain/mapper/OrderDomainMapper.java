package co.khmer.samrouth.order.domain.mapper;

import co.khmer.samrouth.domain.entity.Order;
import co.khmer.samrouth.domain.entity.OrderItem;
import co.khmer.samrouth.order.domain.dto.CommandOrderItem;
import co.khmer.samrouth.order.domain.dto.CreateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDomainMapper {

    @Mapping(source = "customerId",target = "customerId.value")
    @Mapping(source = "businessId",target = "businessId.value")
    @Mapping(source = "price",target = "price.amount")
    Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand);

    @Mapping(source = "productId",target = "product.id.value")
    @Mapping(source = "price",target = "price.amount")
    @Mapping(source = "subTotal",target = "subTotal.amount")
    OrderItem commandOrderItemToOrderItem(CommandOrderItem commandOrderItem);
}
