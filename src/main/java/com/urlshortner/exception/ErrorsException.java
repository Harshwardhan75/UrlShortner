package com.urlshortner.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorsException extends RuntimeException {
    private final Map<String, String> errors;

    public ErrorsException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public ErrorsException(BindingResult result) {
        super("Validation failed");
        this.errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
