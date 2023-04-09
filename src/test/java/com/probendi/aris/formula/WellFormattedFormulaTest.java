package com.probendi.aris.formula;

import com.probendi.aris.exception.ArisException;
import com.probendi.aris.exception.MissingSymbolException;
import com.probendi.aris.token.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WellFormattedFormulaTest {

    private static final AtomicCondition P = new AtomicCondition("P");
    private static final AtomicCondition Q = new AtomicCondition("Q");
    private static final AtomicCondition R = new AtomicCondition("R");
    private static final Negation NOT_P = new Negation(P);
    private static final Negation NOT_Q = new Negation(Q);
    private static final Negation NOT_R = new Negation(R);
    private static final Disjunction DIS_NOT_P_NOT_Q = new Disjunction(NOT_P, NOT_Q);

    @ParameterizedTest
    @ArgumentsSource(DetermineTruthConditionsArgumentsProvider.class)
    void testDetermineTruthConditions(final WellFormedFormula wff, final List<Condition> expected) {
        final List<Condition> conditions = wff.determineTruthnessConditions();
        System.out.println(conditions);
        assertEquals(expected, conditions);
    }

    @ParameterizedTest
    @ArgumentsSource(ValuationArgumentsProvider.class)
    void testValuation(final WellFormedFormula wff, final Map<String, Boolean> values, final boolean expected) throws ArisException {
        assertEquals(expected, wff.valuate(values));
    }

    @Test
    void testMissingSymbolException() {
        final WellFormedFormula wff = new Negation(new AtomicCondition("P"));
        final Map<String, Boolean> values = Map.of("Q", true);
        final MissingSymbolException e = assertThrows(MissingSymbolException.class, () -> wff.valuate(values));
        assertEquals("P", e.getMessage());
    }

    @Test
    void testEmbeddedOperators() throws ArisException {
        final And AND = new And();
        final Atom P = new Atom("P");
        final Atom Q = new Atom("Q");
        final LBracket L_BRACKET = new LBracket();
        final Or OR = new Or();
        final Atom R = new Atom("R");
        final RBracket R_BRACKET = new RBracket();

        final List<Token> line = List.of(
                L_BRACKET,
                L_BRACKET,
                L_BRACKET, P, AND, Q, R_BRACKET,
                OR,
                L_BRACKET, Q, AND, R, R_BRACKET,
                R_BRACKET,
                AND,
                L_BRACKET,
                L_BRACKET, P, OR, Q, R_BRACKET,
                AND,
                L_BRACKET, Q, OR, R, R_BRACKET,
                R_BRACKET,
                R_BRACKET,
                R_BRACKET
        );
        final Queue<Token> tokens = new LinkedList<>(line);

        final WellFormedFormula wff = new Conjunction(
                new Disjunction(
                        new Conjunction(new AtomicCondition("P"), new AtomicCondition("Q")),
                        new Conjunction(new AtomicCondition("Q"), new AtomicCondition("R"))
                ),
                new Conjunction(
                        new Disjunction(new AtomicCondition("P"), new AtomicCondition("Q")),
                        new Disjunction(new AtomicCondition("Q"), new AtomicCondition("R"))
                )
        );
        assertEquals(wff, WellFormedFormula.parse(tokens));
    }

    @ParameterizedTest
    @ArgumentsSource(ValidateTautologyProvider.class)
    void testIsTautology(final WellFormedFormula wff, final boolean expected) {
        final Argument argument = new Argument();
        argument.addPremise(wff);
        assertEquals(expected, argument.isTautology());
    }

    static class ValuationArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of(NOT_P, Map.of("P", true), false),
                    Arguments.of(NOT_P, Map.of("P", false), true),
                    Arguments.of(DIS_NOT_P_NOT_Q, Map.of("P", true, "Q", false), true),
                    Arguments.of(DIS_NOT_P_NOT_Q, Map.of("P", true, "Q", true), false)
            );
        }
    }

    static class DetermineTruthConditionsArgumentsProvider implements ArgumentsProvider {

        private static final AtomicCondition P = new AtomicCondition("P");
        private static final AtomicCondition Q = new AtomicCondition("Q");
        private static final AtomicCondition R = new AtomicCondition("R");
        private static final Negation NOT_P = new Negation(P);
        private static final Negation NOT_Q = new Negation(Q);
        private static final Negation NOT_R = new Negation(R);

        private static final Condition P_FALSE = new AtomicCondition("P").setFalse();
        private static final Condition P_TRUE = new AtomicCondition("P").setTrue();
        private static final Condition Q_FALSE = new AtomicCondition("Q").setFalse();
        private static final Condition Q_TRUE = new AtomicCondition("Q").setTrue();
        private static final Condition R_FALSE = new AtomicCondition("R").setFalse();

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of(P, List.of(P_TRUE)),
                    Arguments.of(NOT_P, List.of(P_FALSE)),
                    Arguments.of(new Negation(NOT_Q), List.of(Q_TRUE)),
                    Arguments.of(new Negation(new Negation(NOT_R)), List.of(R_FALSE)),

                    Arguments.of(new Conjunction(P, Q), List.of(new BinaryCondition(P_TRUE, Q_TRUE))),
                    Arguments.of(new Conjunction(P, NOT_Q), List.of(new BinaryCondition(P_TRUE, Q_FALSE))),
                    Arguments.of(new Conjunction(NOT_P, NOT_Q), List.of(new BinaryCondition(P_FALSE, Q_FALSE))),
                    Arguments.of(new Negation(new Conjunction(P, Q)), List.of(
                            new BinaryCondition(P_FALSE, Q_FALSE),
                            new BinaryCondition(P_FALSE, Q_TRUE),
                            new BinaryCondition(P_TRUE, Q_FALSE)
                    )),
                    Arguments.of(new Negation(new Conjunction(NOT_P, NOT_Q)), List.of(
                            new BinaryCondition(P_TRUE, Q_TRUE),
                            new BinaryCondition(P_TRUE, Q_FALSE),
                            new BinaryCondition(P_FALSE, Q_TRUE)
                    )),
                    Arguments.of(new Disjunction(P, Q), List.of(
                            new BinaryCondition(P_TRUE, Q_FALSE),
                            new BinaryCondition(P_FALSE, Q_TRUE),
                            new BinaryCondition(P_TRUE, Q_TRUE)
                    )),
                    Arguments.of(new Negation(new Disjunction(P, Q)), List.of(new BinaryCondition(P_FALSE, Q_FALSE))),
                    Arguments.of(new Conditional(P, Q), List.of(
                            new BinaryCondition(P_FALSE, Q_FALSE),
                            new BinaryCondition(P_FALSE, Q_TRUE),
                            new BinaryCondition(P_TRUE, Q_TRUE)
                    ))
            );
        }
    }

    static class ValidateTautologyProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of(P, false),
                    Arguments.of(new Conjunction(P, P), false),
                    Arguments.of(new Conjunction(P, new Negation(P)), false),
                    Arguments.of(new Disjunction(P, P), false),
                    Arguments.of(new Disjunction(P, new Negation(P)), true),
                    // ((P ∧ (Q ∨ R)) ∨ (¬P ∧ (¬Q ∨ ¬R)))
                    Arguments.of(new Disjunction(
                            new Conjunction(P, new Disjunction(Q, R)),
                            new Conjunction(NOT_P, new Conjunction(NOT_Q, NOT_R))), false),
                    // (¬(¬(P ∧ Q) ∧ ¬(P ∧ R)) ∨ ¬(P ∧ (Q ∨ R)))
                    Arguments.of(new Disjunction(
                            new Negation(
                                    new Conjunction(
                                            new Negation(new Conjunction(P, Q)),
                                            new Negation(new Conjunction(P, R)))
                            ),
                            new Negation(new Conjunction(P, new Disjunction(Q, R)))
                    ), true)
            );
        }
    }
}

