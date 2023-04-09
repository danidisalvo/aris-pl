package com.probendi.aris.formula;

import com.probendi.aris.exception.ArisException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentTest {

    private static final AtomicCondition P = new AtomicCondition("P");
    private static final AtomicCondition P1 = new AtomicCondition("P1");
    private static final AtomicCondition Q = new AtomicCondition("Q");
    private static final AtomicCondition R = new AtomicCondition("R");
    private static final AtomicCondition S = new AtomicCondition("S");
    private static final Negation NOT_P = new Negation(P);
    private static final Negation NOT_Q = new Negation(Q);
    private static final Negation NOT_R = new Negation(R);
    private static final Negation NOT_S = new Negation(S);

    @ParameterizedTest
    @ArgumentsSource(ValidateArgumentsProvider.class)
    void testIsValid(final Argument argument, final boolean expected) throws ArisException {
        assertEquals(expected, argument.isValid());
    }

    static class ValidateArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    // ¬P, ¬Q, ¬R ∴ S
                    Arguments.of(new Argument(S, NOT_P, NOT_Q, NOT_R), false),
                    // (P ∧ ¬Q), (R ∧ ¬S) ∴ (Q ∨ S)
                    Arguments.of(new Argument(
                                    new Disjunction(Q, S), new Conjunction(P, NOT_Q), new Conjunction(R, NOT_S)),
                            false),
                    // (P ∧ ¬Q) ∴ ¬(Q ∧ R)
                    Arguments.of(new Argument(
                                    new Negation(new Conjunction(Q, R)), new Conjunction(P, NOT_Q)),
                            true),
                    // (P ∨ Q) ∴ P
                    Arguments.of(new Argument(P, new Disjunction(P, Q)), false),
                    // P, ¬(P ∧ ¬Q) ∴ Q
                    Arguments.of(new Argument(Q, P, new Negation(new Conjunction(P, NOT_Q))), true),
                    // (P → Q), (Q → R) ∴ (P → R)
                    Arguments.of(new Argument(
                                    new Conditional(P, R), new Conditional(P, Q), new Conditional(Q, R)),
                            true),
                    // ((P ∧ Q) ∨ R) ∴ ¬(¬P ∨ ¬R)
                    Arguments.of(new Argument(
                                    new Disjunction(NOT_P, NOT_R), new Disjunction(new Conjunction(P, Q), R)),
                            false),
                    // ((P ∧ Q) ∧ R) ∴ (Q ∧ (R ∧ P))
                    Arguments.of(new Argument(
                                    new Conjunction(Q, new Conjunction(R, P)), new Conjunction(new Conjunction(P, Q), R)),
                            true),
                    // ¬(P ∧ ¬Q), ¬(Q ∧ R) ∴ ¬(R ∧ ¬P)
                    Arguments.of(new Argument(
                                    new Negation(new Conjunction(R, NOT_P)),
                                    new Negation(new Conjunction(P, NOT_Q)),
                                    new Negation(new Conjunction(Q, R))),
                            false),
                    // (¬P ∨ R), (P ∨ Q), ¬(Q ∧ ¬S) ∴ (R ∨ S)
                    Arguments.of(new Argument(
                                    new Disjunction(R, S),
                                    new Disjunction(NOT_P, R),
                                    new Disjunction(P, Q),
                                    new Negation(new Conjunction(Q, NOT_S))),
                            true),
                    // ((P → Q) → (R → ¬S)), (¬R → (Q ∧ P1)), (¬P → P1) ∴ (S → P1)
                    Arguments.of(new Argument(
                                    new Conditional(S, P1),
                                    new Conditional(new Conditional(P, Q), new Conditional(R, NOT_S)),
                                    new Conditional(NOT_R, new Conjunction(Q, P1)),
                                    new Conditional(NOT_P, P1)),
                            false)
            );
        }
    }
}
