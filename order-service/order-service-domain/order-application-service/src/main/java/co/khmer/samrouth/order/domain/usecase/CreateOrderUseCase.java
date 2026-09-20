package co.khmer.samrouth.order.domain.usecase;

import co.khmer.samrouth.order.domain.dto.CreateOrderCommand;
import co.khmer.samrouth.order.domain.dto.CreateOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateOrderUseCase {
    public CreateOrderResult execute(CreateOrderCommand createOrderCommand){
        log.info("Executing create order user case :{}",createOrderCommand);
        // Validate customer
        return  new CreateOrderResult(UUID.randomUUID());
    }
}


// Insert Update Deleted -> Command -> Transaction
// Select -> Query -> Transaction read only
// Pattern : CQRS = Command Query Responsibility SegregationL