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
    
    Map<String, Long> localVariables = new HashMap<>();
    long getVariableCount() {
        return localVariables.size();
    }

    List<TIdentifier> declarationArguments = new ArrayList<>();

    boolean hasReturn = false;
}
