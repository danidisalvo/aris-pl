package com.probendi.aris.exception;

/**
 * A {@code UnexpectedEndOfLineException} is thrown by the parser when unexpectedly reaching the end of a line.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedEndOfLineException extends ArisException {

    /**
     * Creates a new UnexpectedEndOfLineException.
     */
    public UnexpectedEndOfLineException() {
        super();
    }

    /**
     * Creates a new UnexpectedEndOfLineException with the given message.
     *
     * @param message the message
     */
    public UnexpectedEndOfLineException(final String message) {
        super(message);
    }

    /**
     * Creates a new UnexpectedEndOfLineException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnexpectedEndOfLineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new UnexpectedEndOfLineException with the given cause.
     *
     * @param cause the cause
     */
    public UnexpectedEndOfLineException(final Throwable cause) {
        super(cause);
    }
}
