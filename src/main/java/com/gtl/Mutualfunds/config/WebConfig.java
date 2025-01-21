package com.gtl.Mutualfunds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Adjust URL to match your Flutter app or domain
        registry.addMapping("/**").allowedOrigins("http://localhost:8080"); // or http://10.0.2.2 if you're using emulator
    }
}
