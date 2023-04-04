package com.probendi.aris.exception;

/**
 * A {@code UnexpectedSymbolException} is thrown when an unexpected symbol is found.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedSymbolException extends ArisException {

    public UnexpectedSymbolException() {
        super();
    }

    public UnexpectedSymbolException(final String message) {
        super(message);
    }

    public UnexpectedSymbolException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnexpectedSymbolException(final Throwable cause) {
        super(cause);
    }
}
