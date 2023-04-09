package com.probendi.aris.exception;

/**
 * A {@code ParserException} is thrown when the parser failed to parse a line.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class ParserException extends ArisException {

    /**
     * Creates a new ParserException.
     */
    public ParserException() {
        super();
    }

    /**
     * Creates a new ParserException with the given message.
     *
     * @param message the message
     */
    public ParserException(final String message) {
        super(message);
    }

    /**
     * Creates a new ParserException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new ParserException with the given cause.
     *
     * @param cause the cause
     */
    public ParserException(final Throwable cause) {
        super(cause);
    }
}
