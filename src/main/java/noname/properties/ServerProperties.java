package noname.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "service.prop")
public class ServerProperties {

    private String port;

    private String ip;

    private String url;

    private String protocol;

    public String getServerUrl() {
        return this.protocol + "://" + this.ip + ":" + this.port;
    }
}
