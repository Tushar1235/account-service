package com.swiftpay.account_service.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Value("${user.service.token}")
    private String token;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return  requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer "+ token);
        };
    }
}
