package com.sjarno.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * can also be used for handling static resources
 */
@Configuration
//@EnableWebMvc
public class CustomCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    /**
     * Configured so that client(angular) side can refresh page by the url provided
      */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
            .addViewController("/heroes")
            .setViewName("forward:/");
        registry
            .addViewController("/dashboard")
            .setViewName("forward:/");
            registry
            .addViewController("/detail/{id:[\\d+]}")
            .setViewName("forward:/");
    }
    



}
