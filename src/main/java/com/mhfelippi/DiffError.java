package com.mhfelippi;

/**
 * Describes an error to the client.
 */
public class DiffError {

    private String error;
    private String message;

    /**
     * Creates a new error information.
     * @param error The error key.
     * @param message The error message.
     */
    public DiffError(String error, String message) {
        this.error = error;
        this.message = message;
    }

    /**
     * Returns the name of the error.
     * @return The name of the error.
     */
    public String getError() {
        return error;
    }

    /**
     * Returns a description message of the error.
     * @return The description message of the error.
     */
    public String getMessage() {
        return message;
    }

}
