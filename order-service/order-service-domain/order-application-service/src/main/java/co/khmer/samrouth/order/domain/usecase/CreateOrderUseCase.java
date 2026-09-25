package co.khmer.samrouth.order.domain.usecase;

import co.khmer.samrouth.domain.entity.Business;
import co.khmer.samrouth.domain.entity.Order;
import co.khmer.samrouth.domain.entity.Product;
import co.khmer.samrouth.domain.event.OrderCreatedEvent;
import co.khmer.samrouth.domain.excetion.OrderDomainException;
import co.khmer.samrouth.domain.service.OrderDomainService;
import co.khmer.samrouth.domain.valueobject.BusinessId;
import co.khmer.samrouth.domain.valueobject.Money;
import co.khmer.samrouth.domain.valueobject.ProductId;
import co.khmer.samrouth.order.domain.dto.CreateOrderCommand;
import co.khmer.samrouth.order.domain.dto.CreateOrderResult;
import co.khmer.samrouth.order.domain.mapper.OrderDomainMapper;
import co.khmer.samrouth.order.domain.port.output.BusinessRepository;
import co.khmer.samrouth.order.domain.port.output.CustomerRepository;
import co.khmer.samrouth.order.domain.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j // using annotation for logging
@RequiredArgsConstructor
public class CreateOrderUseCase {

    // Inject order domain service
    private final OrderDomainService orderDomainService;

    // Inject adapter as port
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;
    private final OrderDomainMapper orderDomainMapper;

    public CreateOrderResult execute(CreateOrderCommand createOrderCommand){
        log.info("Executing create order user case :{}",createOrderCommand);
        // Validate customer
        customerRepository.findCustomer(createOrderCommand.customerId())
                .orElseThrow(() -> new OrderDomainException("Could not find customer with ID: " + createOrderCommand.customerId()));

        // Validate business
        List<Product> products = createOrderCommand.items().stream()
                .map(commandOrderItem -> Product.builder()
                        .id(new ProductId(commandOrderItem.productId()))
                        .price(new Money(commandOrderItem.price()))
                        .build())
                .toList();

        Business business = Business.builder()
                .id(new BusinessId(createOrderCommand.businessId()))
                .products(products)
                .build();

        business = businessRepository.findBusiness(business)
                .orElseThrow(() -> new OrderDomainException("Could not find business with ID: " + createOrderCommand.businessId()));

        log.info("Found business: {}", business);

        // Invoke order domain logic
        Order order = orderDomainMapper.createOrderCommandToOrder(createOrderCommand);
//        log.info("Order price: {}", order.getPrice().getAmount());
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, business);
        log.info("Order created event: {}", orderCreatedEvent.getOrder().getId());

        // Save order into database
        Order savedOrder = orderRepository.saveOrder(order);
        if (savedOrder == null) {
            throw  new OrderDomainException("Could not save order into database");
        }

        return new CreateOrderResult(savedOrder.getId().value());
    }
}


// Insert Update Deleted -> Command -> Transaction
// Select -> Query -> Transaction read only
// Pattern : CQRS = Command Query Responsibility SegregationL