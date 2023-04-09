package com.probendi.aris.exception;

/**
 * A {@code LexicalAnalyzerException} is thrown when the lexical analyzer can't read the input source.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class LexicalAnalyzerIOException extends ArisException {

    /**
     * Creates a new LexicalAnalyzerIOException.
     */
    public LexicalAnalyzerIOException() {
        super();
    }

    /**
     * Creates a new LexicalAnalyzerIOException with the given message.
     *
     * @param message the message
     */
    public LexicalAnalyzerIOException(final String message) {
        super(message);
    }

    /**
     * Creates a new LexicalAnalyzerIOException with the given message and cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public LexicalAnalyzerIOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new LexicalAnalyzerIOException with the given cause.
     *
     * @param cause the cause
     */
    public LexicalAnalyzerIOException(final Throwable cause) {
        super(cause);
    }
}
