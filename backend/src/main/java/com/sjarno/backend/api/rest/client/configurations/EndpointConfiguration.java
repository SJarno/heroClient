package com.sjarno.backend.api.rest.client.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration for different external endpoints.
 * Eases using different profiles with contextual endpoint.
 * 
 */
@Configuration
public class EndpointConfiguration {

    @Getter
    @Setter
    @Value("${api.heroservice.url}")
    private String heroServiceBaseUrl;

    @Getter
    @Setter
    private String[] whitelist = {
            "status", "main-title", 
            "api/rest/heroes/all-heroes", 
            "api/rest/heroes/hero-by-id/",
            "api/rest/heroes/update",
            "api/rest/heroes/create",
            "api/rest/heroes/delete/",
            "api/rest/heroes/heroes-by-name"
    };

}
