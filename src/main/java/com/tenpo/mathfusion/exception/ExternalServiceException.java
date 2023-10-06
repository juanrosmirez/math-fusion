package com.tenpo.mathfusion.exception;

import org.springframework.http.HttpStatusCode;

public class ExternalServiceException extends Exception {

    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceException(HttpStatusCode statusCode, String message, Throwable cause) {
        super(message, cause);
    }
}