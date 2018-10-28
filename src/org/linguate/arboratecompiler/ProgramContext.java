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
import org.linguate.arborate.vm.FunctionDefinition;

/**
 *
 * @author Phil Hutchinson
 */
class ProgramContext {
    Map<String, Long> localFunctions = new HashMap<>();
    long getFunctionCount() {
        return localFunctions.size();
    }
    
    List<FunctionDefinition> functionDefinitions = new ArrayList<>();
}
