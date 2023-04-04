package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code valuate} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Valuate extends Token {

    public Valuate() {
        super(List.of(Identifier.class));
    }

    @Override
    public String toString() {
        return "valuate";
    }
}
