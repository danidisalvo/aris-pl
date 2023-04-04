package com.probendi.aris.formula;

import com.probendi.aris.exception.MissingSymbolException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a disjunction (also known as OR) operation.
 *
 * @param wff1 the first argument
 * @param wff2 the second argument
 *             <p>
 *             {@code @Copyright}, 2023 Daniele Di Salvo
 */
public record Disjunction(WellFormedFormula wff1, WellFormedFormula wff2) implements WellFormedFormula {

    /**
     * Creates a new disjunction of the given well-formed formulae.
     *
     * @param wff1 the left argument as a well-formed formula
     * @param wff2 the right argument as a well-formed formula
     * @throws IllegalArgumentException if a wff is null
     */
    public Disjunction {
        if (wff1 == null) {
            throw new IllegalArgumentException("wff1 cannot be null");
        }
        if (wff2 == null) {
            throw new IllegalArgumentException("wff2 cannot be null");
        }
    }

    @Override
    public List<Condition> determineFalsehoodConditions() {
        return List.of(
                new BinaryCondition(wff1.determineFalsehoodConditions().get(0), wff2.determineFalsehoodConditions().get(0))
        );
    }

    @Override
    public List<Condition> determineTruthnessConditions() {
        return List.of(
                new BinaryCondition(wff1.determineTruthnessConditions().get(0), wff2.determineFalsehoodConditions().get(0)),
                new BinaryCondition(wff1.determineFalsehoodConditions().get(0), wff2.determineTruthnessConditions().get(0)),
                new BinaryCondition(wff1.determineTruthnessConditions().get(0), wff2.determineTruthnessConditions().get(0))
        );
    }

    @Override
    public boolean valuate(final Map<String, Boolean> values) throws MissingSymbolException {
        return wff1.valuate(values) || wff2.valuate(values);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disjunction that)) {
            return false;
        }
        return wff1().equals(that.wff1()) && wff2().equals(that.wff2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(wff1(), wff2());
    }

    @Override
    public String toString() {
        return String.format("(%s ∨ %s)", wff1, wff2);
    }
}
