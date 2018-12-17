/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.linguate.arborate.vm.BaseType;
import org.linguate.arborate.vm.FunctionDefinition;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.InstructionCode;
import org.linguate.arboratecompiler.node.TIdentifier;
/**
 *
 * @author Phil Hutchinson
 */
public class FunctionContext {
    FunctionDefinition def;
    BaseType returnType;
    
    final Stack<IfStatementContext> ifStatementStack = new Stack<>();
    
    private List<Object> instructions = new ArrayList<>();
    List<Instruction> prepareInstructions() {
        // number instructions
        int nextInstructionNumber = 0;
        for(int pos = 0; pos < instructions.size(); pos++) {
            Object item = instructions.get(pos);
            if (item instanceof BranchTarget) {
                ((BranchTarget)item).instructionNumber = nextInstructionNumber;
            }
            else if (item instanceof Instruction) {
                nextInstructionNumber++;
            } else if (item instanceof BranchPlaceholder) {
                nextInstructionNumber++;
            }
        }
        
        List<Instruction> returnValue = new ArrayList<>();
        for(Object item: instructions) {
            if (item instanceof Instruction) {
                returnValue.add((Instruction)item);
            } else if (item instanceof BranchPlaceholder) {
                BranchPlaceholder placeholder = (BranchPlaceholder) item;
                long branchInstructionNumber = placeholder.branchTarget.instructionNumber;
                returnValue.add(new Instruction(placeholder.instructionCode, branchInstructionNumber));
            }
        }
        return returnValue;
    }
    
    void addInstruction(InstructionCode instructionCode) {
        instructions.add(new Instruction(instructionCode));
    }
    
    void addInstruction(InstructionCode instructionCode, Object val) {
        instructions.add(new Instruction(instructionCode, val));
    }
    
    void addBranchTarget(BranchTarget target) {
        instructions.add(target);
    }
    
    void addBranchPlaceholder(BranchPlaceholder placeholder) {
        instructions.add(placeholder);
    }
    
    private List<ScopeContext> scopeStack = new ArrayList<>();
    private List<VariableDefinition> localVariableList = new ArrayList<>();

    void pushScope() {
        scopeStack.add(new ScopeContext());
    }
    
    void popScope() {
        scopeStack.remove(scopeStack.size() - 1);
    }
    
    long addVariable(TIdentifier identifier, BasicType basicType) {
        String variableName = identifier.getText();
        for (int scopePosition = scopeStack.size() - 1; scopePosition >= 0; scopePosition--) {
            ScopeContext scope = scopeStack.get(scopePosition);
            if (scope.localVariables.containsKey(variableName)) {
                String location = identifier.getLine() 
                        + ":" + identifier.getPos();
                throw new RuntimeException("Duplicate argument name: " 
                        + variableName + " at " + location);
            }
        }
        
        long varPos = getVariableCount();
        VariableDefinition varDef = new VariableDefinition(varPos, basicType);
        scopeStack.get(scopeStack.size() - 1).localVariables.put(variableName, varDef);
        localVariableList.add(varDef);
        
        return varPos;
    }
    
    VariableDefinition getVariable(String variableName) {
        for (int scopePosition = scopeStack.size() - 1; scopePosition >= 0; scopePosition--) {
            ScopeContext scope = scopeStack.get(scopePosition);
            if (scope.localVariables.containsKey(variableName)) {
                return scope.localVariables.get(variableName);
            }
        }
        return null;
    }

    VariableDefinition getVariable(long pos) {
        if (localVariableList.size() <= pos) {
            // TODO LOCATION
            throw new RuntimeException("Invalid variable counter)");
        } else {
            return localVariableList.get((int) pos);
        }
    }
    
    long getVariableCount() {
        return localVariableList.size();
    }

    BasicType pendingArgumentType;
    
    long inParameterCount; // will always be the first variables in localVariables
    
    boolean hasReturn = false;
}
