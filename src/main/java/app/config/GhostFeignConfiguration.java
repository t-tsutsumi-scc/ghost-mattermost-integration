package app.config;

import app.config.ApplicationConfig.GhostApi;

import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class GhostFeignConfiguration {

    @Bean
    public RequestInterceptor ghostContentApiKeyRequestInterceptor(final ApplicationConfig appConfig) {
        return requestTemplate -> {
            if (!requestTemplate.queries().containsKey("key")) {
                requestTemplate.query("key", appConfig.ghost().api().key());
            }
        };
    }

    @Bean
    public Retryer ghostRetryer(final ApplicationConfig appConfig) {
        final GhostApi api = appConfig.ghost().api();
        return new Retryer.Default(api.retryPeriodMillis(), api.retryMaxPeriodMillis(), api.retryMaxAttempts());
    }

}
