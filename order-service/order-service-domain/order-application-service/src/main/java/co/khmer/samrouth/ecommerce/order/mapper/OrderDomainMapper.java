package co.khmer.samrouth.ecommerce.order.mapper;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.entity.OrderItem;
import co.khmer.samrouth.ecommerce.order.dto.CommandOrderItem;
import co.khmer.samrouth.ecommerce.order.dto.CreateOrderCommand;
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
