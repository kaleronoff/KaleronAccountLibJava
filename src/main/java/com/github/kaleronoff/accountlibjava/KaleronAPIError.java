package com.github.kaleronoff.accountlibjava;

/**
 * Exception class representing an error related to the Kaleron API.
 * This class extends {@link RuntimeException} and is used to handle API-related errors.
 */
public class KaleronAPIError extends RuntimeException {

    /**
     * Constructs a new KaleronAPIError with a detailed message and an error description.
     *
     * @param message The error message providing context about the exception.
     * @param error   A detailed description of the error returned by the API.
     */
    public KaleronAPIError(String message, String error) {
        super(message + " : " + error);
    }

    /**
     * Constructs a new KaleronAPIError with a message and a throwable cause.
     *
     * @param message The error message providing context about the exception.
     * @param cause   The cause of the exception, typically another throwable.
     */
    public KaleronAPIError(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new KaleronAPIError with a message only.
     *
     * @param message The error message providing context about the exception.
     */
    public KaleronAPIError(String message) {
        super(message);
    }
}
