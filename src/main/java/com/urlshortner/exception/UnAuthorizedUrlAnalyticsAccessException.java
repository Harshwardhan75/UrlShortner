package com.urlshortner.exception;

public class UnAuthorizedUrlAnalyticsAccessException extends RuntimeException {
    public UnAuthorizedUrlAnalyticsAccessException(String message) {
        super(message);
    }
}
