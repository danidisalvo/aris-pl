package com.probendi.aris.exception;

/**
 * A {@code MissingSymbolException} is thrown by the parser when a formula could not be evaluated because a symbol has no value.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class MissingSymbolException extends ArisException {

    /**
     * Creates a new MissingSymbolException.
     */
    public MissingSymbolException() {
        super();
    }

    /**
     * Creates a new MissingSymbolException with the given message.
     *
     * @param message the message
     */
    public MissingSymbolException(final String message) {
        super(message);
    }

    /**
     * Creates a new MissingSymbolException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MissingSymbolException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new MissingSymbolException with the given cause.
     *
     * @param cause the cause
     */
    public MissingSymbolException(final Throwable cause) {
        super(cause);
    }
}
