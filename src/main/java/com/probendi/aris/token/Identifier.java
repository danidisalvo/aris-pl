package com.probendi.aris.token;

import java.util.List;

/**
 * An identifier.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Identifier extends Token {

    /**
     * Creates a new {@code identifier} token with the given value.
     *
     * @param value the value
     */
    public Identifier(final String value) {
        super(List.of(Assign.class), value);
    }
}
