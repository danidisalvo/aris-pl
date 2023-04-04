package com.probendi.aris.exception;

/**
 * A {@code UnexpectedCharacterException} is thrown when an unexpected symbol is found.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedCharacterException extends ArisException {

    public UnexpectedCharacterException() {
        super();
    }

    public UnexpectedCharacterException(final String message) {
        super(message);
    }

    public UnexpectedCharacterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnexpectedCharacterException(final Throwable cause) {
        super(cause);
    }
}
