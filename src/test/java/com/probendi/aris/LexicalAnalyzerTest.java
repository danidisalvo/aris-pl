package com.probendi.aris;

import com.probendi.aris.exception.ArisException;
import com.probendi.aris.exception.UnexpectedCharacterException;
import com.probendi.aris.exception.UnexpectedEndOfLineException;
import com.probendi.aris.exception.UnexpectedSymbolException;
import com.probendi.aris.token.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexicalAnalyzerTest {

    @ParameterizedTest
    @ArgumentsSource(LexicalAnalyzerArgumentsProvider.class)
    void testTokenize(final String line, final List<List<Token>> expected) throws IOException, ArisException {
        final LexicalAnalyzer lexer;
        try (final Reader reader = new StringReader(line)) {
            lexer = new LexicalAnalyzer(reader);
            lexer.tokenize();
        }
        assertEquals(expected, lexer.getTokens());
    }

    @ParameterizedTest
    @ArgumentsSource(LexicalAnalyzerFailsArgumentsProvider.class)
    void testTokenizeFails(final String line, final Class<? extends ArisException> clazz, final String message) throws IOException {
        try (final Reader reader = new StringReader(line)) {
            final LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(reader);
            final ArisException e = assertThrows(ArisException.class, lexicalAnalyzer::tokenize);
            assertEquals(clazz.getSimpleName(), e.getClass().getSimpleName());
            assertEquals(message, e.getMessage());
        }
    }
}

class LexicalAnalyzerArgumentsProvider implements ArgumentsProvider {

    private static final String text = """
            print "Hello, Aris!"
            print
            
            // here we go!
            P := \t  true
             Q := true
            R := true
            argument arg1 := (P & Q) ∴ R
            valuate arg1
            
            argument arg2 := P, ¬(P ∧ ¬Q) ∴ Q
            validate arg2
            
            argument arg3 := (¬(¬(P ∧ Q) ∧ ¬(P ∧ R)) ∨ ¬(P ∧ (Q ∨ R)))
            assert arg3""";

    static final And AND = new And();
    static final Argument ARGUMENT = new Argument();
    static final Assert ASSERT = new Assert();
    static final Assign ASSIGN = new Assign();
    static final Atom P = new Atom("P");
    static final Atom P1 = new Atom("P1");
    static final Atom Q = new Atom("Q");
    static final Atom R = new Atom("R");
    static final Comma COMMA = new Comma();
    static final Identifier ARG_1 = new Identifier("arg1");
    static final Identifier ARG_2 = new Identifier("arg2");
    static final Identifier ARG_3 = new Identifier("arg3");
    static final LBracket L_BRACKET = new LBracket();
    static final Not NOT = new Not();
    static final Or OR = new Or();
    static final Print PRINT = new Print();
    static final RBracket R_BRACKET = new RBracket();
    static final Therefore THEREFORE = new Therefore();
    static final TString HELLO_ARIS = new TString("Hello, Aris!");
    static final True TRUE = new True();
    static final Validate VALIDATE = new Validate();
    static final Valuate VALUATE = new Valuate();

    static final List<List<Token>> tokens = List.of(
            List.of(PRINT, HELLO_ARIS),
            List.of(PRINT),
            List.of(P, ASSIGN, TRUE),
            List.of(Q, ASSIGN, TRUE),
            List.of(R, ASSIGN, TRUE),
            // argument arg1 := (P & Q) ∴ R
            List.of(ARGUMENT, ARG_1, ASSIGN, L_BRACKET, P, AND, Q, R_BRACKET, THEREFORE, R),
            List.of(VALUATE, ARG_1),
            // argument arg2 := P, ¬(P ∧ ¬Q) ∴ Q
            List.of(ARGUMENT, ARG_2, ASSIGN, P, COMMA, NOT, L_BRACKET, P, AND, NOT, Q, R_BRACKET, THEREFORE, Q),
            List.of(VALIDATE, ARG_2),
            // argument arg3 := (¬(¬(P ∧ Q) ∧ ¬(P ∧ R)) ∨ ¬(P ∧ (Q ∨ R)))
            List.of(ARGUMENT, ARG_3, ASSIGN, L_BRACKET, NOT, L_BRACKET, NOT, L_BRACKET, P, AND, Q, R_BRACKET, AND,
                    NOT, L_BRACKET, P, AND, R, R_BRACKET, R_BRACKET, OR, NOT, L_BRACKET, P, AND,
                    L_BRACKET, Q, OR, R, R_BRACKET, R_BRACKET, R_BRACKET),
            List.of(ASSERT, ARG_3));

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Stream.of(
                Arguments.of("P1 := true", List.of(List.of(P1, ASSIGN, TRUE))),
                Arguments.of(text, tokens));
    }
}

class LexicalAnalyzerFailsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Stream.of(
                Arguments.of("P* := true", UnexpectedCharacterException.class,
                        "Unexpected character '*' at position 1 of line 'P* := true'"),
                Arguments.of("argument arg' !P therefore Q", UnexpectedCharacterException.class,
                        "Unexpected character ''' at position 12 of line 'argument arg' !P therefore Q'"),
                Arguments.of("(P >Q)", UnexpectedCharacterException.class,
                        "Unexpected character '>' at position 3 of line '(P >Q)'"),
                Arguments.of("P thereforeQ", UnexpectedCharacterException.class,
                        "Unexpected character 'Q' at position 11 of line 'P thereforeQ'"),
                Arguments.of("Ptherefore Q", UnexpectedCharacterException.class,
                        "Unexpected character 't' at position 1 of line 'Ptherefore Q'"),
                Arguments.of("P is \"All men are mortal", UnexpectedCharacterException.class,
                        "Unexpected character 'l' at position 23 of line 'P is \"All men are mortal'"),
                Arguments.of("P :- true", UnexpectedCharacterException.class,
                        "Unexpected character '-' at position 3 of line 'P :- true'"),
                Arguments.of("P :", UnexpectedCharacterException.class,
                        "Unexpected character ':' at position 2 of line 'P :'"),
                Arguments.of("P Q", UnexpectedSymbolException.class,
                        "Unexpected symbol 'Q' at position 1 of line 'P Q'"),
                Arguments.of("(P & Q)", UnexpectedSymbolException.class,
                        "Unexpected symbol '(' at position 1 of line '(P & Q)'"),
                Arguments.of("!P therefore Q therefore R", UnexpectedSymbolException.class,
                        "Unexpected symbol 'therefore' at position 15 of line '!P therefore Q therefore R'"),
                Arguments.of("P", UnexpectedEndOfLineException.class,
                        "Unexpected end of line at line 'P'")
        );
    }
}
