package com.esgworks.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> invalidFields;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
