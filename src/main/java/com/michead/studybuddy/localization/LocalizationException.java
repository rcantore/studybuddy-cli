package com.michead.studybuddy.localization;

/**
 * Exception thrown when localization operations fail
 */
public class LocalizationException extends RuntimeException {

    public LocalizationException(String message) {
        super(message);
    }

    public LocalizationException(String message, Throwable cause) {
        super(message, cause);
    }
}