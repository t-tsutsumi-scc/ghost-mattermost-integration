package app.config;

import app.config.ApplicationConfig.MattermostApi;

import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class MattermostFeignConfiguration {

    @Bean
    public RequestInterceptor mattermostSessionTokenRequestInterceptor(final ApplicationConfig appConfig) {
        return requestTemplate -> {
            if (!requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + appConfig.mattermost().api().accessToken());
            }
        };
    }

    @Bean
    public Retryer mattermostRetryer(final ApplicationConfig appConfig) {
        final MattermostApi api = appConfig.mattermost().api();
        return new Retryer.Default(api.retryPeriodMillis(), api.retryMaxPeriodMillis(), api.retryMaxAttempts());
    }

}
