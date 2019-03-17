/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arborate.vm.BaseType;
import org.linguate.arboratecompiler.analysis.DepthFirstAdapter;
import org.linguate.arboratecompiler.node.*;

/**
 *
 * @author Phil Hutchinson
 */
public class FirstPassAnalyzer extends DepthFirstAdapter {
    private ProgramContext programCtx;
    private String activeFunctionName;
    private FunctionContext activeFunctionCtx;
    private long activeParamCount;
    private ArborateStructType currentArborateType;

    public FirstPassAnalyzer(ProgramContext programCtx) {
        this.programCtx = programCtx;
    }
    
    public void inAProgram(AProgram node) {
    }
    
    public void inATypeDecl(ATypeDecl node) {
        currentArborateType = new ArborateStructType();
    }

    public void inATypeDeclName(ATypeDeclName node) {
        TIdentifier identifier = node.getIdentifier();
        String newTypeName = identifier.getText();
        if (programCtx.getTypeByName(newTypeName) != null) {
            throw new RuntimeException("Duplicate type name: " + identifier.getText());
        }
        currentArborateType.name = newTypeName;
    }
    
    public void outATypeDeclField(ATypeDeclField node) {
        ATypeDeclFieldType typeNode = (ATypeDeclFieldType)node.getTypeDeclFieldType();
        ATypeDeclFieldName nameNode = (ATypeDeclFieldName) node.getTypeDeclFieldName();
        
        TIdentifier typeIdentifier =  typeNode.getIdentifier();
        String fieldTypeName = typeIdentifier.getText();
        ArborateType fieldType = programCtx.getTypeByName(fieldTypeName);
        if (fieldType == null) {
            throw new RuntimeException("Unknown type: " + fieldTypeName);
        }
        
        TIdentifier nameIdentifier = nameNode.getIdentifier();
        String fieldName = nameIdentifier.getText();
        
        
        currentArborateType.AddField(fieldType, fieldName);
    }
    
    public void outATypeDecl(ATypeDecl node) {
        programCtx.addType(currentArborateType);
    }
    
    public void inAFuncDecl(AFuncDecl node) {
        activeFunctionName = null;
        activeFunctionCtx = new FunctionContext();
    }
    
    public void outAFuncDeclRetType(AFuncDeclRetType node) {
        TIdentifier identifier = node.getIdentifier();
        String returnType = identifier.getText();
        if (returnType.equals(BuiltInType.INTEGER.getName())) {
            activeFunctionCtx.returnType = BuiltInType.INTEGER;
        } else if (returnType.equals(BuiltInType.STRING.getName())) {
            activeFunctionCtx.returnType = BuiltInType.STRING;
        } else if (returnType.equals(BuiltInType.BOOLEAN.getName())) {
            activeFunctionCtx.returnType = BuiltInType.BOOLEAN;
        } else if (returnType.equals(BuiltInType.NODE.getName())) {
            activeFunctionCtx.returnType = BuiltInType.NODE;
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
            activeFunctionCtx.pendingArgumentType = BuiltInType.INTEGER;
        } else if (varType.equals("string")) {
            activeFunctionCtx.pendingArgumentType = BuiltInType.STRING;
        } else if (varType.equals("boolean")) {
            activeFunctionCtx.pendingArgumentType = BuiltInType.BOOLEAN;
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
