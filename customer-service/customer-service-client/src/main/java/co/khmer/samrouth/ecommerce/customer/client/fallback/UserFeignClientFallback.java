package co.khmer.samrouth.ecommerce.customer.client.fallback;

import co.khmer.samrouth.ecommerce.customer.client.dto.UserResponse;
import co.khmer.samrouth.ecommerce.customer.client.exception.UserServiceUnavailableException;
import co.khmer.samrouth.ecommerce.customer.client.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public UserResponse getById(String id) {
        log.info("UserFeignClientFallback getById: {}", id);
        // 1. Get cache from Redis
        // 2. Require or verify (payment ,user)
        throw new UserServiceUnavailableException("User service is unavailable now , pls try again");
    }
}
