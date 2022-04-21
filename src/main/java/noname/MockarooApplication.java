package noname;

import noname.properties.ServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        ServerProperties.class
})
public class MockarooApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockarooApplication.class, args);
    }

}
