/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import org.linguate.arboratecompiler.lexer.Lexer;
import org.linguate.arboratecompiler.lexer.LexerException;
import org.linguate.arboratecompiler.node.Start;
import org.linguate.arboratecompiler.parser.Parser;
import org.linguate.arboratecompiler.parser.ParserException;

/**
 *
 * @author Phil Hutchinson
 */
public class Compiler {
    public static Start compile(String input) throws LexerException, ParserException, IOException {
        Lexer lexer = new Lexer(new PushbackReader(new StringReader(input)));
        //Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(fileName)), 1024));
        Parser parser = new Parser(lexer);

        Start ast = parser.parse();

        return ast;
    }
}
