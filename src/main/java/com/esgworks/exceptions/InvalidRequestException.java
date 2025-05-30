package com.esgworks.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InvalidRequestException extends RuntimeException {
    private List<String> invalidFields;

    public InvalidRequestException(String message, List<String> invalidFields) {
        super(message);
        this.invalidFields = invalidFields;
    }

}