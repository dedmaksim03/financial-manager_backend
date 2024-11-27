package com.example.financialmanager.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применяем ко всем URL
                .allowedOriginPatterns("http://*") // Разрешаем все домены
                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowPrivateNetwork(true)
        ; // Разрешенные методы
    }
}
