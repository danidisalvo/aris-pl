package com.probendi.aris.formula;

/**
 * A binary condition.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public record BinaryCondition(Condition c1, Condition c2) implements Condition {

    /**
     * Creates a new binary condition.
     *
     * @param c1 the first or left condition
     * @param c2 the second or right condition
     */
    public BinaryCondition {
        if (c1 == null) {
            throw new IllegalArgumentException("c1 cannot be null");
        }
        if (c2 == null) {
            throw new IllegalArgumentException("c2 cannot be null");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BinaryCondition pair)) {
            return false;
        }
        return c1.equals(pair.c1) && c2.equals(pair.c2);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", c1, c2);
    }
}
