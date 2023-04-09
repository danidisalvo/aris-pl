package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code false} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class False extends Token {

    /**
     * Creates a new {@code false} token.
     */
    public False() {
        super(List.of());
    }

    @Override
    public String toString() {
        return "false";
    }
}
