package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code argument} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Argument extends Token {

    /**
     * Creates a new {@code argument} token.
     */
    public Argument() {
        super(List.of(Identifier.class));
    }

    @Override
    public String toString() {
        return "argument";
    }
}
