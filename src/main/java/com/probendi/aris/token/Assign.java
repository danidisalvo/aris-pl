package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code :=} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Assign extends Token {

    /**
     * Creates a new {@code :=} token.
     */
    public Assign() {
        super(List.of(Atom.class, False.class, LBracket.class, Not.class, True.class));
    }

    @Override
    public String toString() {
        return ":=";
    }
}
