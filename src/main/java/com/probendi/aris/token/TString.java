package com.probendi.aris.token;

import java.util.List;

/**
 * A string.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class TString extends Token {

    /**
     * Creates a new {@code string} token with the given value.
     *
     * @param value the value
     */
    public TString(final String value) {
        super(List.of(), value);
    }

    @Override
    public String toString() {
        return value;
    }
}
