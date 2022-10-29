package com.sjarno.backend.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractResponse<T> {
    private String type;
    private String status;
    private T body;
    private String message;
    private String error;
}
