package com.probendi.aris.formula;

import com.probendi.aris.exception.MissingSymbolException;

import java.util.*;

/**
 * A propositional logic argument.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Argument implements WellFormedFormula {

    final private List<WellFormedFormula> premises = new LinkedList<>();
    private WellFormedFormula conclusion;

    public Argument() {
    }

    /**
     * Creates a new argument with the given premises and conclusion.
     *
     * @param conclusion the conclusion
     * @param premises   the premises
     * @throws IllegalArgumentException if conclusion or premises is {@code null}
     */
    public Argument(final WellFormedFormula conclusion, final WellFormedFormula... premises) {
        if (conclusion == null) {
            throw new IllegalArgumentException("conclusion cannot be null");
        }
        if (premises == null) {
            throw new IllegalArgumentException("premises cannot be null or empty");
        }
        this.conclusion = conclusion;
        this.premises.addAll(List.of(premises));
    }

    public List<WellFormedFormula> getPremises() {
        return premises;
    }

    public WellFormedFormula getConclusion() {
        return conclusion;
    }

    public void setConclusion(final WellFormedFormula conclusion) {
        if (conclusion == null) {
            throw new IllegalArgumentException("conclusion cannot be null");
        }
        this.conclusion = conclusion;
    }

    public void addPremise(final WellFormedFormula premise) {
        if (premise == null) {
            throw new IllegalArgumentException("premise cannot be null");
        }
        premises.add(premise);
    }

    /**
     * Returns {@code true} if this argument is a tautology.
     *
     * @return {@code true} if the given well-formed formula is a tautology
     */
    public boolean isTautology() throws MissingSymbolException {
        if (conclusion != null || premises.size() != 1) {
            final String msg = "this method can be only invoked on arguments without conclusion and exactly one premises";
            throw new UnsupportedOperationException(msg);
        }

        final WellFormedFormula wff = getPremises().get(0);
        final Set<String> vars = new HashSet<>();
        for (final Condition condition : wff.determineTruthnessConditions()) {
            for (final Condition atomicCondition : getAtomicConditions(condition)) {
                vars.add(((AtomicCondition) atomicCondition).getValue());
            }
        }

        for (final Map<String, Boolean> values : generateTruthTable(vars)) {
            if (!wff.valuate(values)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Returns {@code true} if this argument is valid, i.e., if there is a relevant valuation which makes the premises
     * and the negation of the conclusion all true.
     *
     * @return {@code true} if this argument is valid
     */
    public boolean isValid() throws MissingSymbolException {
        final List<WellFormedFormula> formulae = new LinkedList<>();
        formulae.add(new Negation(conclusion));
        formulae.addAll(premises);

        final Set<String> vars = new HashSet<>();
        for (final WellFormedFormula wff : formulae) {
            for (final Condition condition : wff.determineTruthnessConditions()) {
                for (final Condition atomicCondition : getAtomicConditions(condition)) {
                    vars.add(((AtomicCondition) atomicCondition).getValue());
                }
            }
        }

        for (final Map<String, Boolean> values : generateTruthTable(vars)) {
            boolean valid = true;
            for (final WellFormedFormula wff : formulae) {
                if (!wff.valuate(values)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Condition> determineFalsehoodConditions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Condition> determineTruthnessConditions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean valuate(final Map<String, Boolean> values) throws MissingSymbolException {
        boolean p = true;
        for (final WellFormedFormula premise : premises) {
            p = premise.valuate(values);
            if (!p) break;
        }
        return p & conclusion.valuate(values);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Argument argument)) {
            return false;
        }
        return getPremises().equals(argument.getPremises()) && Objects.equals(getConclusion(), argument.getConclusion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPremises(), getConclusion());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final WellFormedFormula premise : premises) {
            sb.append(premise).append(", ");
        }
        return sb.isEmpty() ? "" : String.format("%s ∴ %s", sb.substring(0, sb.length() - 2), conclusion);
    }

    private List<Map<String, Boolean>> generateTruthTable(final Set<String> vars) {
        final List<String> varsList = new LinkedList<>(vars);
        final List<Map<String, Boolean>> result = new LinkedList<>();
        final int n = varsList.size();
        for (int i = 0; n > 0 && i != (1 << n); i++) {
            final StringBuilder sb = new StringBuilder(Integer.toBinaryString(i));
            while (sb.length() != n) {
                sb.insert(0, '0');
            }
            final Map<String, Boolean> map = new HashMap<>();
            for (int j = 0; j < n; j++) {
                map.put(varsList.get(j), sb.charAt(j) == '1');
            }
            result.add(map);
        }
        return result;
    }

    private List<Condition> getAtomicConditions(final Condition condition) {
        final List<Condition> atomicConditions = new LinkedList<>();
        if (condition instanceof AtomicCondition) {
            atomicConditions.add(condition);
        } else {
            final BinaryCondition b = (BinaryCondition) condition;
            atomicConditions.addAll(getAtomicConditions(b.c1()));
            atomicConditions.addAll(getAtomicConditions(b.c2()));
        }
        return atomicConditions;
    }
}