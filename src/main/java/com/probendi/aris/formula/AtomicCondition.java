package com.probendi.aris.formula;

import com.probendi.aris.exception.MissingSymbolException;
import com.probendi.aris.token.Atom;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An atomic condition.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class AtomicCondition extends Atom implements Condition, WellFormedFormula {

    private Boolean boolValue;

    public AtomicCondition(final String value) {
        super(value);
    }

    public AtomicCondition setFalse() {
        boolValue = false;
        return this;
    }

    public AtomicCondition setTrue() {
        boolValue = true;
        return this;
    }

    @Override
    public List<Condition> determineFalsehoodConditions() {
        return List.of(new AtomicCondition(value).setFalse());
    }

    @Override
    public List<Condition> determineTruthnessConditions() {
        return List.of(new AtomicCondition(value).setTrue());
    }

    @Override
    public boolean valuate(final Map<String, Boolean> values) throws MissingSymbolException {
        if (values == null) {
            throw new IllegalArgumentException("values cannot be null or empty");
        }
        if (!values.containsKey(value)) {
            throw new MissingSymbolException(value);
        }
        return values.get(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtomicCondition atomic)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(boolValue, atomic.boolValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), boolValue);
    }

    @Override
    public String toString() {
        return boolValue == null ? value : (value + "=" + boolValue);
    }
}
