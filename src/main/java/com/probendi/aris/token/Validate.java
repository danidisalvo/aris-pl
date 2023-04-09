package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code validate} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Validate extends Token {

    /**
     * Creates a new {@code validate} token.
     */
    public Validate() {
        super(List.of(Identifier.class));
    }

    @Override
    public String toString() {
        return "validate";
    }
}
