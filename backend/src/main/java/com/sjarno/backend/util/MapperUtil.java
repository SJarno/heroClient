package com.sjarno.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjarno.backend.api.rest.client.exceptions.ApiError;

@Component
public class MapperUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public ApiError convertJsonToApiError(String json) {
        ApiError apiError = null;
        try {
            return this.objectMapper.readValue(json, ApiError.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return apiError;
    }
    
}
