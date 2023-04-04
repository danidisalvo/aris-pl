package com.probendi.aris.exception;

/**
 * An {@code ArisException} is thrown when an error occurs executing {@code Aris}.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class ArisException extends Exception {

    public ArisException() {
        super();
    }

    public ArisException(final String message) {
        super(message);
    }

    public ArisException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ArisException(final Throwable cause) {
        super(cause);
    }
}
