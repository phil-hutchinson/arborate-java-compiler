/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    Map<String, Long> localFunctions = new HashMap<>();
    long functionCount = 0;

    List<Instruction> instructions;
    Map<String, Long> localVariables;
    long variableCount;
    List<TIdentifier> declarationArguments;
    boolean hasReturn;
    
    List<FunctionDefinition> functionDefinitions = new ArrayList<>();
    
    TIdentifier declarationIdentifier;
    Long assignmentVariablePosition;

    TIdentifier functionCallNameIdentifier;
    
    /*****************  NODE DEFINITIONS  *****************/

    public void inAFuncDecl(AFuncDecl node) {
        instructions = new ArrayList<Instruction>();
        localVariables = new HashMap<>();
        variableCount = 0;
        hasReturn = false;
    }
    
    public void inAFuncDeclName(AFuncDeclName node) {
        TIdentifier identifier = node.getIdentifier();
        String funcName = identifier.getText();
        if (localFunctions.containsKey(funcName)) {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate Function name: " + funcName + " at " + location);
        }
        localFunctions.put(funcName, functionCount);
    }

    public void inAFuncDeclArgList(AFuncDeclArgList node) {
        declarationArguments = new ArrayList<>();
    }
    
    public void inAFuncDeclArgType(AFuncDeclArgType node) {
        String varType = node.getIdentifier().getText();
        if (!varType.equals("int")) {
            throw new RuntimeException("int is the only valid argument type at the moment.");
        }
    }
    
    public void inAFuncDeclArgName(AFuncDeclArgName node) {
        declarationArguments.add(node.getIdentifier());
    }
    
    public void outAFuncDeclArgList(AFuncDeclArgList node) {
        for (TIdentifier argIdentifier : declarationArguments) {
            String varName = argIdentifier.getText();
            if (localVariables.containsKey(varName)) {
                String location = declarationIdentifier.getLine() 
                        + ":" + declarationIdentifier.getPos();
                throw new RuntimeException("Duplicate argument name: " 
                        + varName + " at " + location);
            }
            localVariables.put(varName, variableCount);
            
            variableCount++;
        }

        List<TIdentifier> reversed = new ArrayList(declarationArguments);
        Collections.reverse(reversed);
        
        long argPos = declarationArguments.size();
        
        for(TIdentifier argIdentifier: reversed) {
            argPos--;
            addInstruction(InstructionCode.STACK_TO_VARIABLE, argPos);
        }
    }
    
    
    public void outAFuncDecl(AFuncDecl node) {
        FunctionDefinition newFunc = new FunctionDefinition(instructions, (int)variableCount, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        functionDefinitions.add(newFunc);
        instructions = null;
        functionCount++;
        if (!hasReturn) {
            throw new RuntimeException("function missing return statement");
        }
    }

    public void inADeclarationStatement(ADeclarationStatement node) {
        declarationIdentifier = null;
    }
    
    public void inAVarDeclType(AVarDeclType node) {
        String varType = node.getIdentifier().getText();
        if (!varType.equals("int")) {
            throw new RuntimeException("int is the only valid variable type at the moment.");
        }
    }
    
    public void inAVarDeclName(AVarDeclName node) {
        declarationIdentifier = node.getIdentifier();
    }
    
    public void outADeclarationStatement(ADeclarationStatement node) {
        String varName = declarationIdentifier.getText();
        if (localVariables.containsKey(varName)) {
            String location = declarationIdentifier.getLine() 
                    + ":" + declarationIdentifier.getPos();
            throw new RuntimeException("Duplicate Variable name: " 
                    + varName + " at " + location);
        }
        localVariables.put(varName, variableCount);
        
        addInstruction(InstructionCode.INTEGER_TO_STACK, 0L);
        addInstruction(InstructionCode.STACK_TO_VARIABLE, variableCount);
        
        variableCount++;
    }
    
    public void inAAssignmentStatement(AAssignmentStatement node) {
        assignmentVariablePosition = null;
    }
    
    public void inAVarAssignName(AVarAssignName node) {
        TIdentifier identifier = node.getIdentifier();
        String varNameToAssign = identifier.getText();
        if (localVariables.containsKey(varNameToAssign)) {
            assignmentVariablePosition = localVariables.get(varNameToAssign);
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Assignment to unknown variable or before variable declaration: " + varNameToAssign + " at " + location);
        }
    }
    
    public void outAAssignmentStatement(AAssignmentStatement node) {
        addInstruction(InstructionCode.STACK_TO_VARIABLE, (long)assignmentVariablePosition);
    }
    
    public void outAReturnStatement(AReturnStatement node) {
        hasReturn = true;
        addInstruction(InstructionCode.EXIT_FUNCTION);
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
    
    public void inAFuncCallValue(AFuncCallValue node) {
        functionCallNameIdentifier = null;
    }
    
    public void outAFuncCallName(AFuncCallName node) {
        functionCallNameIdentifier = node.getIdentifier();
    }
    
    public void outAFuncCallValue(AFuncCallValue node) {
        String funcToCall = functionCallNameIdentifier.getText();
        if (localFunctions.containsKey(funcToCall)) {
            long functionNumber = localFunctions.get(funcToCall);
            addInstruction(InstructionCode.CALL_FUNCTION, functionNumber);
        } else {
            String location = functionCallNameIdentifier.getLine() + ":" + functionCallNameIdentifier.getPos();
            throw new RuntimeException("Unknown function: " + funcToCall + " at " + location);
        }
    }
    
    public void inAVarFetchName(AVarFetchName node) {
        TIdentifier identifier = node.getIdentifier();
        String varNameToFetch = identifier.getText();
        if (localVariables.containsKey(varNameToFetch)) {
            long varPos = localVariables.get(varNameToFetch);
            addInstruction(InstructionCode.VARIABLE_TO_STACK, varPos);
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Attempt to use unknown variable or before variable declaration: " + varNameToFetch + " at " + location);
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
