package com.mhfelippi;

/**
 * Exception thrown when a not handled checked exception is throws.
 */
public class UnhandledException extends RuntimeException {

    /**
     * Creates a new <code>UnhandledException</code>.
     * @param cause The suppressed exception.
     */
    public UnhandledException(Throwable cause) {
        super(cause);
    }

}
