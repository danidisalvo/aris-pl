package com.probendi.aris.exception;

/**
 * A {@code LexicalAnalyzerException} is thrown when the lexical analyzer can't read the input source.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
@SuppressWarnings("unused")
public class LexicalAnalyzerIOException extends ArisException {

    public LexicalAnalyzerIOException() {
        super();
    }

    public LexicalAnalyzerIOException(final String message) {
        super(message);
    }

    public LexicalAnalyzerIOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LexicalAnalyzerIOException(final Throwable cause) {
        super(cause);
    }
}
