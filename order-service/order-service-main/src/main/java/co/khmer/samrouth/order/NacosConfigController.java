package co.khmer.samrouth.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/nacos/config")
//@RequestScope
@RequiredArgsConstructor
public class NacosConfigController {
//    @Value("${service.name}")
//    private String serviceName;

//    @Value("${service.info}")
//    private String serviceInfo;

//    @Value("${service.version}")
//    private String serviceVersion;

    private final NacosConfigProps nacosConfigProps;

    @GetMapping
    public Map<String, Object> getConfig() {
        return Map.of(
            "serviceName", nacosConfigProps.getName(),
            "serviceInfo", nacosConfigProps.getInfo(),
            "serviceVersion", nacosConfigProps.getVersion()
        );
    }
}
