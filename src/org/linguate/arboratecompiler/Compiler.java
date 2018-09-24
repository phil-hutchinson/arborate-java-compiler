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
import java.util.List;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.InstructionCode;
import org.linguate.arboratecompiler.lexer.Lexer;
import org.linguate.arboratecompiler.lexer.LexerException;
import org.linguate.arboratecompiler.node.AAddExpr;
import org.linguate.arboratecompiler.node.ADivideExpr;
import org.linguate.arboratecompiler.node.AIntLit;
import org.linguate.arboratecompiler.node.AMultiplyExpr;
import org.linguate.arboratecompiler.node.ASubtractExpr;
import org.linguate.arboratecompiler.node.PExpr;
import org.linguate.arboratecompiler.node.PIntLit;
import org.linguate.arboratecompiler.node.Start;
import org.linguate.arboratecompiler.parser.Parser;
import org.linguate.arboratecompiler.parser.ParserException;
/**
 *
 * @author Phil Hutchinson
 */
public class Compiler {
    public static List<Instruction> compile(String input) throws LexerException, ParserException, IOException {
        Lexer lexer = new Lexer(new PushbackReader(new StringReader(input)));
        //Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(fileName)), 1024));
        Parser parser = new Parser(lexer);

        Start ast = parser.parse();

        List<Instruction> returnValue = new ArrayList<Instruction>();
        
        PExpr expr = ast.getPExpr();
        
        AIntLit op1;
        AIntLit op2;
        if (expr instanceof AAddExpr) {
            AAddExpr addExpr = (AAddExpr) expr;
            returnValue.add(IntegerConstantInstruction(addExpr.getOp1()));
            returnValue.add(IntegerConstantInstruction(addExpr.getOp2()));
            returnValue.add(new Instruction(InstructionCode.INTEGER_ADD));
        } else if (expr instanceof ASubtractExpr) {
            ASubtractExpr subtractExpr = (ASubtractExpr) expr;
            returnValue.add(IntegerConstantInstruction(subtractExpr.getOp1()));
            returnValue.add(IntegerConstantInstruction(subtractExpr.getOp2()));
            returnValue.add(new Instruction(InstructionCode.INTEGER_SUBTRACT));
        } else if (expr instanceof AMultiplyExpr) {
            AMultiplyExpr multiplyExpr = (AMultiplyExpr) expr;
            returnValue.add(IntegerConstantInstruction(multiplyExpr.getOp1()));
            returnValue.add(IntegerConstantInstruction(multiplyExpr.getOp2()));
            returnValue.add(new Instruction(InstructionCode.INTEGER_MULTIPLY));
        } else if (expr instanceof ADivideExpr) {
            ADivideExpr divideExpr = (ADivideExpr) expr;
            returnValue.add(IntegerConstantInstruction(divideExpr.getOp1()));
            returnValue.add(IntegerConstantInstruction(divideExpr.getOp2()));
            returnValue.add(new Instruction(InstructionCode.INTEGER_DIVIDE));
        } else {
            throw new RuntimeException("Unknown Operator");
        }
        
        return returnValue;
    }

    private static Instruction IntegerConstantInstruction(PIntLit pIntLit) {
        AIntLit aIntLit = (AIntLit) pIntLit;
        long val = Long.parseLong(aIntLit.getIntString().getText());
        return new Instruction(InstructionCode.INTEGER_TO_STACK, val);
    }
}

