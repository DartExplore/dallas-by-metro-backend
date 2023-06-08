package com.dart.explore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public abstract class SecurityConfig {

    @Autowired
    private Environment env;

    protected void configure(HttpSecurity http) throws Exception {
        String apiKey = env.getProperty("API_KEY");
        http
                .addFilterBefore(new ApiKeyFilter(apiKey), AuthorizationFilter.class)
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .requiresChannel()
                .anyRequest();
    }
}

@Configuration
@Profile("dev")
class DevSecurityConfig extends SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configure(http);
        http.csrf().disable();
        return http.build();
    }
}

@Configuration
@Profile("!dev")
class ProdSecurityConfig extends SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configure(http);
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        return http.build();
    }
}