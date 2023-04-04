package com.probendi.aris.token;

import java.util.List;

/**
 * An identifier.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Identifier extends Token {

    public Identifier(final String value) {
        super(List.of(Assign.class), value);
    }
}
