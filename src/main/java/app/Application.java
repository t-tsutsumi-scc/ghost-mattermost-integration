package app;

import app.config.ImmutableApplicationConfig;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({ImmutableApplicationConfig.class})
@EnableScheduling
@EnableFeignClients
@EnableCaching
public class Application {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplicationBuilder(Application.class)
                .bannerMode(Banner.Mode.OFF).build();
        application.run(args);
    }

}
