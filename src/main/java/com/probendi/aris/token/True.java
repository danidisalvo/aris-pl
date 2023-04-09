package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code true} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class True extends Token {

    /**
     * Creates a new {@code true} token.
     */
    public True() {
        super(List.of());
    }

    @Override
    public String toString() {
        return "true";
    }
}
