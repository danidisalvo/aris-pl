package com.probendi.aris;

import com.probendi.aris.exception.*;
import com.probendi.aris.token.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * {@code LexicalAnalyzer} lexically analyses the input data and produces a list of tokens.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class LexicalAnalyzer {

    private static final String UNEXPECTED_CHARACTER = "Unexpected character '%c' at position %d of line '%s'";
    private static final String UNEXPECTED_END_OF_LINE = "Unexpected end of line at line '%s'";
    private static final String UNEXPECTED_SYMBOL = "Unexpected symbol '%s' at position %d of line '%s'";

    // associate a single character to a token
    private static final Map<Character, Class<? extends Token>> oneCharacterTokenMap = new HashMap<>();

    static {
        oneCharacterTokenMap.put('(', LBracket.class);
        oneCharacterTokenMap.put(')', RBracket.class);
        oneCharacterTokenMap.put('∧', And.class);
        oneCharacterTokenMap.put('&', And.class);
        oneCharacterTokenMap.put(',', Comma.class);
        oneCharacterTokenMap.put('→', MaterialImplication.class);
        oneCharacterTokenMap.put('⊃', MaterialImplication.class);
        oneCharacterTokenMap.put('¬', Not.class);
        oneCharacterTokenMap.put('~', Not.class);
        oneCharacterTokenMap.put('!', Not.class);
        oneCharacterTokenMap.put('∨', Or.class);
        oneCharacterTokenMap.put('|', Or.class);
        oneCharacterTokenMap.put('∴', Therefore.class);
    }

    // associate a two characters sequence to a token, e.g. := to the assign token
    private static final Map<Character, Map<Character, Class<? extends Token>>> twoCharactersTokenMap = Map.of(
            ':', Map.of('=', Assign.class),
            '-', Map.of('>', MaterialImplication.class)
    );

    // associate a keyword to a token
    private static final Map<String, Class<? extends Token>> keywords = Map.of(
            "argument", Argument.class,
            "assert", Assert.class,
            "false", False.class,
            "print", Print.class,
            "therefore", Therefore.class,
            "true", True.class,
            "validate", Validate.class,
            "valuate", Valuate.class
    );

    private final Reader reader;

    private final Queue<Queue<Token>> tokens = new LinkedList<>();

    /**
     * Creates a new lexical analyzer for the given input reader.
     *
     * @param reader the input reader
     * @throws IllegalArgumentException if reader is {@code null}
     */
    public LexicalAnalyzer(final Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader cannot be null");
        }
        this.reader = reader;
    }

    public Queue<Queue<Token>> getTokens() {
        return tokens;
    }

    /**
     * Tokenizes the lines read from the given input reader.
     *
     * @throws ArisException if an error occurs
     */
    public void tokenize() throws ArisException {
        String line;
        try (final BufferedReader br = new BufferedReader(reader)) {
            while ((line = br.readLine()) != null) {
                final Map<String, Boolean> counts = new HashMap<>();
                boolean inAtom = false;
                boolean inIdentifier = false;
                boolean inString = false;
                StringBuilder sb = new StringBuilder();

                final int n = line.indexOf("//");
                final String str = n != -1 ? line.substring(0, n) : line;
                final Queue<Token> queue = new LinkedList<>();

                for (int i = 0; i < str.length(); i++) {
                    final char c = str.charAt(i);

                    // first we deal with some special cases
                    if (inAtom) {
                        if (c == ' ' || c == '\t' || c == '&' || c == ')' || c == ',' || c == ':' || c == '-' || c == '|') {
                            queue.add(new Atom(sb.toString()));
                            inAtom = false;
                        } else if ((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '\'') {
                            sb.append(c);
                            continue;
                        } else {
                            throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, c, i, line));
                        }
                    }

                    if (inIdentifier) {
                        if (c == ' ' || c == '\t') {
                            queue.add(new Identifier(sb.toString()));
                            inIdentifier = false;
                        } else if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_') {
                            sb.append(c);
                            continue;
                        } else {
                            throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, c, i, line));
                        }
                    }

                    if (inString) {
                        if (c == '"') {
                            queue.add(new TString(sb.toString()));
                            inString = false;
                        } else {
                            if (i == str.length() - 1) {
                                throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, c, i, line));
                            }
                            sb.append(c);
                        }
                        continue;
                    }

                    // spaces are ignored except when parsing a string
                    if (c == ' ' || c == '\t') continue;

                    if (c == '"') {
                        inString = true;
                        sb = new StringBuilder();
                    } else if (oneCharacterTokenMap.containsKey(c)) {
                        queue.add(oneCharacterTokenMap.get(c).getDeclaredConstructor().newInstance());
                    } else if (twoCharactersTokenMap.containsKey(c)) {
                        try {
                            final char nc = str.charAt(++i);
                            if (twoCharactersTokenMap.get(c).containsKey(nc)) {
                                queue.add(twoCharactersTokenMap.get(c).get(nc).getDeclaredConstructor().newInstance());
                            } else {
                                throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, nc, i, line));
                            }
                        } catch (final IndexOutOfBoundsException e) {
                            throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, c, i - 1, line));
                        }
                    } else if (c >= 'A' && c <= 'Z') {
                        inAtom = true;
                        sb = new StringBuilder(String.valueOf(c));
                    } else if (c >= 'a' && c <= 'z') {
                        // search for a keyword
                        boolean found = false;
                        final String s = str.substring(i);
                        for (final String k : keywords.keySet()) {
                            if (s.startsWith(k)) {
                                queue.add(keywords.get(k).getDeclaredConstructor().newInstance());
                                i += k.length();
                                if (i < str.length() && str.charAt(i) != ' ' && str.charAt(i) != '\t') {
                                    throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, str.charAt(i), i, line));
                                }

                                if ("argument".equals(k) || "print".equals(k) || "therefore".equals(k) || "valuate".equals(k)) {
                                    if (counts.containsKey(k)) {  // there can be only one symbol per line
                                        throw new UnexpectedSymbolException(String.format(UNEXPECTED_SYMBOL, k, i - k.length(), line));
                                    } else {
                                        counts.put(k, true);
                                    }
                                }

                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            inIdentifier = true;
                            sb = new StringBuilder(String.valueOf(c));
                        }
                    } else {
                        throw new UnexpectedCharacterException(String.format(UNEXPECTED_CHARACTER, c, i, line));
                    }
                }

                // an atom or an identifier can be the last item of a line
                if (inAtom) {
                    queue.add(new Atom(sb.toString()));
                } else if (inIdentifier) {
                    queue.add(new Identifier(sb.toString()));
                }

                if (queue.isEmpty()) continue;

                // validates the line's syntax
                int i = 1;
                final Queue<Token> list = new LinkedList<>();
                Token token;
                while ((token = queue.poll()) != null) {
                    if (list.isEmpty()) {
                        final Token nextToken = queue.peek();
                        if (!(token instanceof Print) && nextToken == null) {
                            throw new UnexpectedEndOfLineException(String.format(UNEXPECTED_END_OF_LINE, line));
                        }
                        if (token instanceof Atom) {
                            if (!(nextToken instanceof Assign)) {
                                throw new UnexpectedSymbolException(String.format(UNEXPECTED_SYMBOL, nextToken, i, line));
                            }
                        } else if (!(token instanceof Argument || token instanceof Assert ||
                                token instanceof Print || token instanceof Validate || token instanceof Valuate)) {
                            throw new UnexpectedSymbolException(String.format(UNEXPECTED_SYMBOL, token, i, line));
                        }
                    }
                    final Token nextToken = queue.peek();
                    if (nextToken == null || token.canFollow(nextToken)) {
                        list.add(token);
                    } else {
                        throw new UnexpectedSymbolException(String.format(UNEXPECTED_SYMBOL, nextToken, i, line));
                    }
                    i++;
                }
                tokens.add(list);
            }
        } catch (final IOException | ReflectiveOperationException e) {
            throw new LexicalAnalyzerIOException(e);
        }
    }
}
