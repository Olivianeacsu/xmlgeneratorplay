package org.example.xmlgeneratorplay.exception;

public class SchemaFileNotFoundException extends RuntimeException {
    public SchemaFileNotFoundException(String message) {
        super(message);
    }

    public SchemaFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
