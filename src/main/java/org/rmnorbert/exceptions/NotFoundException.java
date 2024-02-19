package org.rmnorbert.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Searched element: " + message + " not found");
    }
}
