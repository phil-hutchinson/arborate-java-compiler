/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.linguate.arborate.vm.BaseType;
import org.linguate.arborate.vm.FunctionDefinition;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.InstructionCode;
import org.linguate.arborate.vm.VirtualMachine;
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
    public static List<FunctionDefinition> compile(String input) throws LexerException, ParserException, IOException {
        Lexer lexer = new Lexer(new PushbackReader(new StringReader(input)));
        //Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(fileName)), 1024));
        Parser parser = new Parser(lexer);

        Start ast = parser.parse();

        ProgramContext programCtx = new ProgramContext();
                
        FirstPassAnalyzer firstPassAnalyzer = new FirstPassAnalyzer(programCtx);
        ast.apply(firstPassAnalyzer);
        
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(programCtx);
        ast.apply(semanticAnalyzer);
        
        return programCtx.getFunctionList();
    }
}

