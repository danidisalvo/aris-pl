package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code print} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Print extends Token {

    /**
     * Creates a new {@code print} token.
     */
    public Print() {
        super(List.of(TString.class));
    }

    @Override
    public String toString() {
        return "print";
    }
}
