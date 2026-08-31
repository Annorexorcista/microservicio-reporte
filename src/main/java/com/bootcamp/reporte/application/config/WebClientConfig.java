package com.bootcamp.reporte.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient capabilityWebClient(
            WebClient.Builder builder,
            @Value("${capability.service.url:http://localhost:8081}") String url) {
        return builder.baseUrl(url).build();
    }
}
