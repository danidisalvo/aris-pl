package com.probendi.aris.exception;

/**
 * A {@code MissingSymbolException} is thrown by the parser when a formula could not be evaluated because a symbol has no value.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class MissingSymbolException extends ArisException {

    public MissingSymbolException() {
        super();
    }

    public MissingSymbolException(final String message) {
        super(message);
    }

    public MissingSymbolException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MissingSymbolException(final Throwable cause) {
        super(cause);
    }
}
