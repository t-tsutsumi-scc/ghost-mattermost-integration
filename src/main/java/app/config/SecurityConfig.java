package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests(r -> r.antMatchers("/auth/**").authenticated().anyRequest().permitAll())
                .oauth2Login(c -> {
                    // Do nothing
                }).logout(c -> {
                    c.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                            .logoutSuccessHandler((request, response, authentication) -> {
                                String logoutSuccessUrl = request.getParameter("returnUrl");
                                if (!UrlUtils.isValidRedirectUrl(logoutSuccessUrl)) {
                                    logoutSuccessUrl = "/login?logout";
                                }
                                response.sendRedirect(logoutSuccessUrl);
                            });
                }).csrf(c -> {
                    c.disable();
                });
    }

}
