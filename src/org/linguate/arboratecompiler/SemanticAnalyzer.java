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
    Map<String, Integer> localFunctions = new HashMap<>();
    int nextFunctionNumber = 0;
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
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_ADD);
    }

    public void outASubtractExpr(ASubtractExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_SUBTRACT);
    }

    public void outAMultiplyExpr(AMultiplyExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_MULTIPLY);
    }

    public void outADivideExpr(ADivideExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_DIVIDE);
    }

    /**********************  HELPER METHODS  *********************/
    private static long processIntLit(PIntLit pIntLit) {
        AIntLit aIntLit = (AIntLit) pIntLit;
        return Long.parseLong(aIntLit.getIntString().getText());
    }
    
    private void addInstructionByCode(InstructionCode instructionCode) {
        instructions.add(new Instruction(instructionCode));
    }
    
    private void addInstructionIntToStack(long val) {
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, val));
    }
    
    
}
