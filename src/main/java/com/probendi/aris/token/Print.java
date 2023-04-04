package com.probendi.aris.token;

import java.util.List;

/**
 * The {@code print} token.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Print extends Token {

    public Print() {
        super(List.of());
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
