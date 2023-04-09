package com.probendi.aris.formula;

import com.probendi.aris.exception.MissingSymbolException;
import com.probendi.aris.exception.UnexpectedSymbolException;
import com.probendi.aris.token.*;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * A well-formed formula of a propositional logic language.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public interface WellFormedFormula {

    /**
     * Returns the conditions which make this formula false.
     *
     * @return the conditions which make this formula false
     */
    List<Condition> determineFalsehoodConditions();

    /**
     * Returns the conditions which make this formula true.
     *
     * @return the conditions which make this formula true
     */
    List<Condition> determineTruthnessConditions();

    /**
     * Valuates this formula.
     *
     * @param values a map containing the symbols' truth-values
     * @return {@code true} if this formula preserves the truth
     * @throws IllegalArgumentException if values is {@code null}
     * @throws MissingSymbolException   if this formula could not be evaluated because a symbol has no value
     */
    boolean valuate(final Map<String, Boolean> values) throws MissingSymbolException;

    /**
     * Parses a well-formed formula from the given tokens.
     *
     * @param tokens the tokens to be parsed
     * @return a well-formed formula
     * @throws UnexpectedSymbolException if an unexpected symbol was found
     */
    static WellFormedFormula parse(final Queue<Token> tokens) throws UnexpectedSymbolException {
        Token token;
        if ((token = tokens.peek()) != null) {
            if (token instanceof Comma || token instanceof RBracket || token instanceof Therefore) {
                return null;
            }
            token = tokens.remove();
            if (token instanceof Atom) {
                return new AtomicCondition(token.getValue());
            } else if (token instanceof Not) {
                return new Negation(parse(tokens));
            } else if (token instanceof LBracket) {
                return parseBinary(tokens);
            }
        }
        throw new UnexpectedSymbolException();
    }

    /**
     * Parses a well-formed binary formula from the given tokens.
     *
     * @param tokens the tokens to be parsed
     * @return a well-formed binary formula
     * @throws UnexpectedSymbolException if an unexpected symbol was found
     */
    private static WellFormedFormula parseBinary(final Queue<Token> tokens) throws UnexpectedSymbolException {
        final WellFormedFormula wff1 = parse(tokens);
        final Token operator = tokens.remove();
        final WellFormedFormula wff2 = parse(tokens);
        final WellFormedFormula formula;
        if (operator instanceof And) {
            formula = new Conjunction(wff1, wff2);
        } else if (operator instanceof Or) {
            formula = new Disjunction(wff1, wff2);
        } else {
            formula = new Conditional(wff1, wff2);
        }
        tokens.remove();
        return formula;
    }
}
