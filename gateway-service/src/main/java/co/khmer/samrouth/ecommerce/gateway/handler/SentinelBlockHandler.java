package co.khmer.samrouth.ecommerce.gateway.handler;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class SentinelBlockHandler implements BlockRequestHandler {

    @PostConstruct
    public void register(){
        GatewayCallbackManager.setBlockHandler(this);
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        if(ex instanceof DegradeException){
            Map<String, Object> result = new HashMap<>();
            result.put("code", 503);
            result.put("message", "ISTAD Report: Service Unavailable");
            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(result);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 429);
        result.put("message", "ISTAD Report: Request count exceeds the maximum limit");
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(result);
    }
}
