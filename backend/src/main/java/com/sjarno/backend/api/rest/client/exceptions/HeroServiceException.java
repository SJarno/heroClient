package com.sjarno.backend.api.rest.client.exceptions;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.Getter;
import lombok.Setter;

public class HeroServiceException extends RuntimeException{

    @Getter
    @Setter
    private WebClientResponseException webClientResponseException;

    public HeroServiceException() {
    }

    public HeroServiceException(String message) {
        super(message);
    }
    
}
