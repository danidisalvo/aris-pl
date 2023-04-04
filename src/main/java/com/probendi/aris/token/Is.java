package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code is} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Is extends Token {

    public Is() {
        super(List.of(TString.class));
    }

    @Override
    public String toString() {
        return "is";
    }
}
