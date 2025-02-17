package ru.isands.test.estore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost") // Разрешает только localhost
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешает методы
                .allowedHeaders("*"); // Разрешает все заголовки
    }
}
