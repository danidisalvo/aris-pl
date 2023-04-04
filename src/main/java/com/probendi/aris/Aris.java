package com.probendi.aris;

import com.probendi.aris.exception.ArisException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * {@code Aris} is a propositional logic interpreter.
 * <p>
 * {@code @Copyright}, 2023 Daniele Di Salvo
 */
public class Aris {

    private static final String USAGE = "Usage: java -jar aris-pl-1.0.jar file";

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE);
        }
        try (final Reader reader = Files.newBufferedReader(Path.of(args[0]))) {
            final LexicalAnalyzer lexer = new LexicalAnalyzer(reader);
            lexer.tokenize();
            final Parser parser = new Parser();
            parser.parse(lexer.getTokens());
        } catch (final ArisException | IOException e) {
            e.printStackTrace();
        }
    }
}
