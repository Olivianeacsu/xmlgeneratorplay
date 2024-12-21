package org.example.xmlgeneratorplay.exception;

public class XMLValidationException extends RuntimeException {
    public XMLValidationException(String message) {
        super(message);
    }

    public XMLValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
