package com.probendi.aris;

import com.probendi.aris.exception.ArisException;
import com.probendi.aris.formula.*;
import com.probendi.aris.token.Token;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.probendi.aris.LexicalAnalyzerTest.tokens;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    private static final AtomicCondition P = new AtomicCondition("P");
    private static final AtomicCondition Q = new AtomicCondition("Q");
    private static final AtomicCondition R = new AtomicCondition("R");

    @Test
    void testParsing() throws ArisException {
        // prepare the test data set
        final Queue<Queue<Token>> queue = new LinkedList<>();
        for (final List<Token> line : tokens) {
            queue.add(new LinkedList<>(line));
        }

        final Parser parser = new Parser();
        parser.parse(queue);

        final Map<String, WellFormedFormula> arguments = new HashMap<>();

        // (P ∧ Q) ∴ R
        Argument argument = new Argument();
        argument.addPremise(new Conjunction(P, Q));
        argument.setConclusion(R);
        arguments.put("arg1", argument);

        // P, ¬(P ∧ ¬Q) ∴ Q
        argument = new Argument();
        argument.addPremise(P);
        argument.addPremise(new Negation(new Conjunction(P, new Negation(Q))));
        argument.setConclusion(Q);
        arguments.put("arg2", argument);

        // (¬(¬(P ∧ Q) ∧ ¬(P ∧ R)) ∨ ¬(P ∧ (Q ∨ R)))
        argument = new Argument();
        argument.addPremise(new Disjunction(
                new Negation(new Conjunction(new Negation(new Conjunction(P, Q)), new Negation(new Conjunction(P, R)))),
                new Negation(new Conjunction(P, new Disjunction(Q, R)))));
        arguments.put("arg3", argument);

        assertEquals(arguments, parser.getArguments());

        final Map<String, Boolean> values = Map.of("P", true, "Q", true, "R", true);
        assertEquals(values, parser.getValues());

        final Map<String, Boolean> valuations = Map.of("arg1", true);
        assertEquals(valuations, parser.getValuations());

        final Map<String, Boolean> validations = Map.of("arg2", true);
        assertEquals(validations, parser.getValidations());

        final Map<String, Boolean> assertions = Map.of("arg3", true);
        assertEquals(assertions, parser.getAssertions());
    }
}

