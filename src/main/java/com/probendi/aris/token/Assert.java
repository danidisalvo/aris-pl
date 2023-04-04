package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code assert} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Assert extends Token {

    public Assert() {
        super(List.of(Identifier.class));
    }

    @Override
    public String toString() {
        return "assert";
    }
}
