package co.khmer.samrouth.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
@Setter
@Getter
@NoArgsConstructor
public class NacosConfigProps {
    private String name;
    private String info;
    private String version;
}
