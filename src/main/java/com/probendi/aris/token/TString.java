package com.probendi.aris.token;

import java.util.List;

/**
 * A string.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class TString extends Token {

    public TString(final String value) {
        super(List.of(), value);
    }

    @Override
    public String toString() {
        return "\"" + super.toString() + "\"";
    }
}
