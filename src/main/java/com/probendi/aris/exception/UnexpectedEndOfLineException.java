package com.probendi.aris.exception;

/**
 * A {@code UnexpectedEndOfLineException} is thrown by the parser when unexpectedly reaching the end of a line.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class UnexpectedEndOfLineException extends ArisException {

    public UnexpectedEndOfLineException() {
        super();
    }

    public UnexpectedEndOfLineException(final String message) {
        super(message);
    }

    public UnexpectedEndOfLineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnexpectedEndOfLineException(final Throwable cause) {
        super(cause);
    }
}
