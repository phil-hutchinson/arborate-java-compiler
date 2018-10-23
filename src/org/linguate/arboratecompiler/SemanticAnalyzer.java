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
    ProgramContext programBuilder;
    
    List<Instruction> instructions;
    Map<String, Long> localVariables;
    long getVariableCount() {
        return localVariables.size();
    }
    
    List<TIdentifier> declarationArguments;
    boolean hasReturn;
    
    List<FunctionDefinition> functionDefinitions = new ArrayList<>();
    
    TIdentifier declarationIdentifier;
    Long assignmentVariablePosition;

    TIdentifier functionCallNameIdentifier;
    
    /*****************  NODE DEFINITIONS  *****************/
    public void inAProgram(AProgram node) {
        programBuilder = new ProgramContext();
    }
    
    public void inAFuncDecl(AFuncDecl node) {
        instructions = new ArrayList<Instruction>();
        localVariables = new HashMap<>();
        hasReturn = false;
    }
    
    public void inAFuncDeclName(AFuncDeclName node) {
        TIdentifier identifier = node.getIdentifier();
        String funcName = identifier.getText();
        if (programBuilder.localFunctions.containsKey(funcName)) {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate Function name: " + funcName + " at " + location);
        }
        programBuilder.localFunctions.put(funcName, programBuilder.getFunctionCount());
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
            localVariables.put(varName, getVariableCount());
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
        FunctionDefinition newFunc = new FunctionDefinition(instructions, (int)getVariableCount(), Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        functionDefinitions.add(newFunc);
        instructions = null;
        if (!hasReturn) {
            throw new RuntimeException("function missing return statement");
        }
    }
    
    public void inPStatement(PStatement node) {
        int abc = 3;
    }
    
    public void inADeclarationStatement(ADeclarationStatement node) {
        declarationIdentifier = null;
    }
    
    public void inAVarDeclType(AVarDeclType node) {
        String declaredType = node.getIdentifier().getText();
        BasicType varType; 
        if (declaredType.equals("int")) {

        }
        else if (declaredType.equals("string")) {
            
        } else {
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
        long varPos = getVariableCount();
        localVariables.put(varName, varPos);
        
        addInstruction(InstructionCode.INTEGER_TO_STACK, 0L);
        addInstruction(InstructionCode.STACK_TO_VARIABLE, varPos);
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

    public void outAMultiplyTerm(AMultiplyTerm node) {
        addInstruction(InstructionCode.INTEGER_MULTIPLY);
    }

    public void outADivideTerm(ADivideTerm node) {
        addInstruction(InstructionCode.INTEGER_DIVIDE);
    }

    public void inAIntLitFactor(AIntLitFactor node) {
        long val = Long.parseLong(node.getIntString().getText());
        addInstruction(InstructionCode.INTEGER_TO_STACK, val);
    }
    
    public void inAFuncCallFactor(AFuncCallFactor node) {
        functionCallNameIdentifier = null;
    }
    
    public void outAFuncCallName(AFuncCallName node) {
        functionCallNameIdentifier = node.getIdentifier();
    }
    
    public void outAFuncCallFactor(AFuncCallFactor node) {
        String funcToCall = functionCallNameIdentifier.getText();
        if (programBuilder.localFunctions.containsKey(funcToCall)) {
            long functionNumber = programBuilder.localFunctions.get(funcToCall);
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
