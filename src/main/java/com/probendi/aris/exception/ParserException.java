package com.probendi.aris.exception;

/**
 * A {@code ParserException} is thrown when the parser failed to parse a line.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class ParserException extends ArisException {

    public ParserException() {
        super();
    }

    public ParserException(final String message) {
        super(message);
    }

    public ParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParserException(final Throwable cause) {
        super(cause);
    }
}
