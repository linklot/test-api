package au.com.llk.testapi;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
        val factory = new UndertowServletWebServerFactory();
        factory.addBuilderCustomizers(builder -> {
            log.info("UNDERTOW - Set buffer size to 32K");
            val bufferSize = 32 * 1024;
            builder.setBufferSize(bufferSize);
        });

        return factory;
    }

}
