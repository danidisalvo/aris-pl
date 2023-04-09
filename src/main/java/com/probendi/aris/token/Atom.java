package com.probendi.aris.token;

import java.util.List;

/**
 * An atom.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Atom extends Token {

    /**
     * Creates a new {@code atom} with the given value.
     *
     * @param value the value
     */
    public Atom(final String value) {
        super(List.of(
                And.class,
                Assign.class,
                Comma.class,
                MaterialImplication.class,
                LBracket.class,
                Or.class,
                RBracket.class,
                Therefore.class), value
        );
    }
}
