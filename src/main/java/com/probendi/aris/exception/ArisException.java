package com.probendi.aris.exception;

/**
 * An {@code ArisException} is thrown when an error occurs executing {@code Aris}.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class ArisException extends Exception {

    /**
     * Creates a new ArisException.
     */
    public ArisException() {
        super();
    }

    /**
     * Creates a new ArisException with the given message.
     *
     * @param message the message
     */
    public ArisException(final String message) {
        super(message);
    }

    /**
     * Creates a new ArisException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ArisException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new ArisException with the given cause.
     *
     * @param cause the cause
     */
    public ArisException(final Throwable cause) {
        super(cause);
    }
}
