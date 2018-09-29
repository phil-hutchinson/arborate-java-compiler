/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.linguate.arborate.vm.BaseType;
import org.linguate.arborate.vm.FunctionDefinition;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.InstructionCode;
import org.linguate.arboratecompiler.analysis.DepthFirstAdapter;
import org.linguate.arboratecompiler.node.*;

/**
 *
 * @author Phil Hutchinson
 */
public class SemanticAnalyzer extends DepthFirstAdapter {
    List<Instruction> instructions;
    Map<String, Long> localFunctions = new HashMap<>();
    long nextFunctionNumber = 0;
    List<FunctionDefinition> functionDefinitions = new ArrayList<>();
    
    /*****************  NODE-PROCESSING METHODS  *****************/
    public void inAFuncName(AFuncName node) {
        TIdentifier identifier = node.getIdentifier();
        String funcName = identifier.getText();
        if (localFunctions.containsKey(funcName)) {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate Function name: " + funcName + " at " + location);
        }
        localFunctions.put(funcName, nextFunctionNumber);
    }
    
    public void inAFunc(AFunc node) {
        instructions = new ArrayList<Instruction>();
    }
    
    public void outAFunc(AFunc node) {
        FunctionDefinition newFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        functionDefinitions.add(newFunc);
        instructions = null;
        nextFunctionNumber++;
    }
    
    public void outAAddExpr(AAddExpr node) {
        addInstruction(InstructionCode.INTEGER_ADD);
    }

    public void outASubtractExpr(ASubtractExpr node) {
        addInstruction(InstructionCode.INTEGER_SUBTRACT);
    }

    public void outAMultiplyExpr(AMultiplyExpr node) {
        addInstruction(InstructionCode.INTEGER_MULTIPLY);
    }

    public void outADivideExpr(ADivideExpr node) {
        addInstruction(InstructionCode.INTEGER_DIVIDE);
    }

    public void inAIntLitValue(AIntLitValue node) {
        long val = Long.parseLong(node.getIntString().getText());
        addInstruction(InstructionCode.INTEGER_TO_STACK, val);
    }
    
    public void outAFuncCallName(AFuncCallName node) {
        TIdentifier identifier = node.getIdentifier();
        String funcToCall = node.getIdentifier().getText();
        if (localFunctions.containsKey(funcToCall)) {
            long functionNumber = localFunctions.get(funcToCall);
            addInstruction(InstructionCode.CALL_FUNCTION, functionNumber);
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Unknown function: " + funcToCall + " at " + location);
        }
    }
    
    /**********************  HELPER METHODS  *********************/
    private void addInstruction(InstructionCode instructionCode) {
        instructions.add(new Instruction(instructionCode));
    }
    
    private void addInstruction(InstructionCode instructionCode, Object val) {
        instructions.add(new Instruction(instructionCode, val));
    }
    
    
}
