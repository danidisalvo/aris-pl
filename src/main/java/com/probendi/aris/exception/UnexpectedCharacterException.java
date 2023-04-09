package com.probendi.aris.exception;

/**
 * A {@code UnexpectedCharacterException} is thrown when an unexpected symbol is found.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedCharacterException extends ArisException {

    /**
     * Creates a new UnexpectedCharacterException.
     */
    public UnexpectedCharacterException() {
        super();
    }

    /**
     * Creates a new UnexpectedCharacterException with the given message.
     *
     * @param message the message
     */
    public UnexpectedCharacterException(final String message) {
        super(message);
    }

    /**
     * Creates a new UnexpectedCharacterException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnexpectedCharacterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new UnexpectedCharacterException with the given cause.
     *
     * @param cause the cause
     */
    public UnexpectedCharacterException(final Throwable cause) {
        super(cause);
    }
}
