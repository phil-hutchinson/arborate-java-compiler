/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arborate.vm.BaseType;
import org.linguate.arboratecompiler.analysis.DepthFirstAdapter;
import org.linguate.arboratecompiler.node.AFuncDecl;
import org.linguate.arboratecompiler.node.AFuncDeclArgList;
import org.linguate.arboratecompiler.node.AFuncDeclArgName;
import org.linguate.arboratecompiler.node.AFuncDeclArgType;
import org.linguate.arboratecompiler.node.AFuncDeclName;
import org.linguate.arboratecompiler.node.AFuncDeclRetType;
import org.linguate.arboratecompiler.node.AProgram;
import org.linguate.arboratecompiler.node.TIdentifier;

/**
 *
 * @author Phil Hutchinson
 */
public class FirstPassAnalyzer extends DepthFirstAdapter {
    private ProgramContext programCtx;
    private String activeFunctionName;
    private FunctionContext activeFunctionCtx;
    private long activeParamCount;

    public FirstPassAnalyzer(ProgramContext programCtx) {
        this.programCtx = programCtx;
    }
    
    public void inAProgram(AProgram node) {
    }
    
    public void inAFuncDecl(AFuncDecl node) {
        activeFunctionName = null;
        activeFunctionCtx = new FunctionContext();
    }
    
    public void outAFuncDeclRetType(AFuncDeclRetType node) {
        TIdentifier identifier = node.getIdentifier();
        String returnType = identifier.getText();
        if (returnType.equals("int")) {
            activeFunctionCtx.returnType = BaseType.INTEGER;
        } else if (returnType.equals("string")) {
            activeFunctionCtx.returnType = BaseType.STRING;
        } else if (returnType.equals("boolean")) {
            activeFunctionCtx.returnType = BaseType.BOOLEAN;
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Unknown return type for function at " + location);
        }
    }
    
    public void inAFuncDeclName(AFuncDeclName node) {
        TIdentifier identifier = node.getIdentifier();
        activeFunctionName = identifier.getText();
        if (programCtx.hasFunctionName(activeFunctionName)) {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate Function name: " + activeFunctionName + " at " + location);
        }
    }

    public void inAFuncDeclArgType(AFuncDeclArgType node) {
        String varType = node.getIdentifier().getText();
        if (varType.equals("int")) {
            activeFunctionCtx.pendingArgumentType = BasicType.Integer;
        } else if (varType.equals("string")) {
            activeFunctionCtx.pendingArgumentType = BasicType.String;
        } else if (varType.equals("boolean")) {
            activeFunctionCtx.pendingArgumentType = BasicType.Boolean;
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Unrecognized type in function parameter: " + varType);
        }
        
        activeFunctionCtx.inParameterCount++;
    }
    
    public void inAFuncDeclArgName(AFuncDeclArgName node) {
        activeFunctionCtx.addParameter(node.getIdentifier(), activeFunctionCtx.pendingArgumentType);
    }
    
    public void outAFuncDeclArgList(AFuncDeclArgList node) {
    }
    
    
    public void outAFuncDecl(AFuncDecl node) {
        programCtx.addFunction(activeFunctionName, activeFunctionCtx, node);
    }
    
}
