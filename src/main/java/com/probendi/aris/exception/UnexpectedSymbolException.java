package com.probendi.aris.exception;

/**
 * A {@code UnexpectedSymbolException} is thrown when an unexpected symbol is found.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedSymbolException extends ArisException {

    /**
     * Creates a new UnexpectedSymbolException.
     */
    public UnexpectedSymbolException() {
        super();
    }

    /**
     * Creates a new UnexpectedSymbolException with the given message.
     *
     * @param message the message
     */
    public UnexpectedSymbolException(final String message) {
        super(message);
    }

    /**
     * Creates a new UnexpectedSymbolException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnexpectedSymbolException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new UnexpectedSymbolException with the given cause.
     *
     * @param cause the cause
     */
    public UnexpectedSymbolException(final Throwable cause) {
        super(cause);
    }
}
