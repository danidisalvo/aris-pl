package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code ∧} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class And extends Token {

    public And() {
        super(List.of(Atom.class, LBracket.class, Not.class));
    }

    @Override
    public String toString() {
        return "∧";
    }
}
