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
import org.linguate.arborate.vm.Instruction;
import org.linguate.arboratecompiler.node.TIdentifier;

/**
 *
 * @author Phil Hutchinson
 */
public class FunctionContext {
    List<Instruction> instructions = new ArrayList<>();
    
    private Map<String, VariableDefinition> localVariables = new HashMap<>();

    long addVariable(TIdentifier identifier, BasicType basicType) {
        String variableName = identifier.getText();
        if (localVariables.containsKey(variableName)) {
            String location = identifier.getLine() 
                    + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate argument name: " 
                    + variableName + " at " + location);
        }
        long varPos = getVariableCount();
        VariableDefinition varDef = new VariableDefinition(varPos, basicType);
        localVariables.put(variableName, varDef);
        return varPos;
    }
    
    VariableDefinition getVariable(String variableName) {
        if (localVariables.containsKey(variableName)) {
            return localVariables.get(variableName);
        } else {
            return null;
        }
        
    }

    long getVariableCount() {
        return localVariables.size();
    }

    
    List<TIdentifier> declarationArguments = new ArrayList<>();

    boolean hasReturn = false;
}
