package com.dart.explore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private Environment env;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String apiKey = env.getProperty("API_KEY");
        http
                .addFilterBefore(new ApiKeyFilter(apiKey), AuthorizationFilter.class)
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .requiresChannel()
                .anyRequest()
                .requiresSecure();

        return http.build();
    }
}
