package com.probendi.aris.formula;

import com.probendi.aris.exception.MissingSymbolException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a negation (also known as NOT) operation.
 *
 * @param wff the argument
 *            <p>
 *            {@code @Copyright}, 2023 Daniele Di Salvo
 */
public record Negation(WellFormedFormula wff) implements WellFormedFormula {

    /**
     * Creates a new negation with the given well-formed formula.
     *
     * @param wff the symbol
     * @throws IllegalArgumentException if wff is null
     */
    public Negation {
        if (wff == null) {
            throw new IllegalArgumentException("wff cannot be null");
        }
    }

    @Override
    public List<Condition> determineFalsehoodConditions() {
        return wff.determineTruthnessConditions();
    }

    @Override
    public List<Condition> determineTruthnessConditions() {
        return wff.determineFalsehoodConditions();
    }

    @Override
    public boolean valuate(final Map<String, Boolean> values) throws MissingSymbolException {
        return !wff.valuate(values);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Negation negation)) {
            return false;
        }
        return wff().equals(negation.wff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(wff());
    }

    @Override
    public String toString() {
        return "¬" + wff;
    }
}
