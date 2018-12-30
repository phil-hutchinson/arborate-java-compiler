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
import java.util.stream.Collectors;
import org.linguate.arborate.vm.FunctionDefinition;
import org.linguate.arboratecompiler.node.AFuncDecl;

/**
 *
 * @author Phil Hutchinson
 */
class ProgramContext {
    private Map<String, Long> nameToFunctionMap = new HashMap<>();
    private Map<AFuncDecl, Long> nodeToFunctionMap = new HashMap<>();
    private List<FunctionContext> allFunctionCtx = new ArrayList<>();
    
    private long getFunctionCount() {
        return nameToFunctionMap.size();
    }
    
    public boolean hasFunctionName(String funcName) {
        return nameToFunctionMap.containsKey(funcName);
    }
    
    public long getFunctionNumberByName(String funcName) {
        return nameToFunctionMap.get(funcName);
    }
    
    public long addFunction(String funcName, FunctionContext funcCtx, AFuncDecl node) {
        long functionNumber = getFunctionCount();
        nameToFunctionMap.put(funcName, functionNumber);
        nodeToFunctionMap.put(node, functionNumber);
        allFunctionCtx.add(funcCtx);
        return functionNumber;
    }
    
    public FunctionContext getFunctionCtxByNode(AFuncDecl node) {
        if(nodeToFunctionMap.containsKey(node)) {
            long functionNumber = nodeToFunctionMap.get(node);
            return allFunctionCtx.get((int)functionNumber);
        } else {
            return null;
        }
    }
    
    public FunctionContext getFunctionCtxByNumber(long functionNumber) {
        return allFunctionCtx.get((int)functionNumber);
    }
    
    public List<FunctionDefinition> getFunctionList() {
        return allFunctionCtx.stream().map(functionCtx -> functionCtx.def).collect(Collectors.toList());
    }
}
