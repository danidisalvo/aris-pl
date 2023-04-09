package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code )} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class RBracket extends Token {

    /**
     * Creates a new {@code )} token.
     */
    public RBracket() {
        super(List.of(And.class, MaterialImplication.class, Comma.class, Or.class, RBracket.class, Therefore.class));
    }

    @Override
    public String toString() {
        return ")";
    }
}
