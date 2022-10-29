package com.sjarno.backend.api.rest.client.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sjarno.backend.util.MapperUtil;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MapperUtil mapper;

    @ExceptionHandler(HeroServiceException.class)
    protected ResponseEntity<Object> handleHeroServiceException(HeroServiceException ex, WebRequest request) {
        List<String> errors = new ArrayList<>() {
            {
                add(ex.getLocalizedMessage());
            }
        };
        ApiError apiError = mapper.convertJsonToApiError(ex.getWebClientResponseException().getResponseBodyAsString());
        
        apiError.getErrors().addAll(errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        // TODO: check for nullpointer, typesafety 
        String errorMessage = ex.getName() + " should be type of " + ex.getRequiredType().getName();
        List<String> errors = new ArrayList<>() {
            {
                add(errorMessage);
                
            }
        };

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Client error",
                request.toString(),
                ex.getLocalizedMessage(),
                errors);

        return handleExceptionInternal(ex, apiError,
                new HttpHeaders(), apiError.getStatus(), request);

    }
}
