package com.sjarno.backend.api.rest.client.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String serviceName;
    private String requestDetails;
    private String message;
    private List<String> errors;
}
