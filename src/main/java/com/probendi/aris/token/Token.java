package com.probendi.aris.token;

import java.util.List;
import java.util.Objects;

/**
 * A token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public abstract class Token {

    private final List<Class<? extends Token>> validTokens;

    /**
     * The token's value, which could be e.g., an identifier's name, a string's content etc.
     */
    protected String value;

    /**
     * Creates a new token with the list of tokens which can follow it.
     *
     * @param validTokens the list of tokens which can follow this token
     */
    protected Token(final List<Class<? extends Token>> validTokens) {
        this.validTokens = validTokens;
    }

    /**
     * Creates a new token with the list of tokens which can follow it and a value.
     *
     * @param validTokens the list of tokens which can follow this token
     * @param value       the value
     */
    protected Token(final List<Class<? extends Token>> validTokens, final String value) {
        this.validTokens = validTokens;
        this.value = value;
    }

    /**
     * Returns the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns {@code true} if the given next token can follow this token.
     *
     * @param next the next token
     * @return if the given next token can follow this token
     * @throws IllegalArgumentException if next is {@code null}
     */
    public boolean canFollow(final Token next) {
        if (next == null) {
            throw new IllegalArgumentException("next cannot be null");
        }
        return validTokens.contains(next.getClass());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token token)) {
            return false;
        }
        return Objects.equals(getValue(), token.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return value;
    }
}
