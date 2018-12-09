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
import java.util.Stack;
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
    ProgramContext programCtx;
    FunctionContext activeFunctionCtx;
    DeclarationStatementContext declarationStatementCtx;
    AssignmentStatementContext assignmentStatementCtx;
    ReturnStatementContext returnStatementCtx;
    Stack<FunctionCallContext> functionCallCtxStack = new Stack<>();
    Stack<Map<Node,EtfContext>> etfCtxStack = new Stack<>();
            
    /*****************  NODE DEFINITIONS  *****************/
    public void inAProgram(AProgram node) {
        programCtx = new ProgramContext();
    }
    
    public void inAFuncDecl(AFuncDecl node) {
        activeFunctionCtx = new FunctionContext();
    }
    
    public void outAFuncDeclRetType(AFuncDeclRetType node) {
        TIdentifier identifier = node.getIdentifier();
        String returnType = identifier.getText();
        if (returnType.equals("int")) {
            activeFunctionCtx.returnType = BaseType.INTEGER;
        } else if (returnType.equals("string")) {
            activeFunctionCtx.returnType = BaseType.STRING;
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Unknown return type for function at " + location);
        }
    }
    
    public void inAFuncDeclName(AFuncDeclName node) {
        TIdentifier identifier = node.getIdentifier();
        String funcName = identifier.getText();
        if (programCtx.localFunctions.containsKey(funcName)) {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Duplicate Function name: " + funcName + " at " + location);
        }
        programCtx.localFunctions.put(funcName, programCtx.getFunctionCount());
    }

    public void inAFuncDeclArgList(AFuncDeclArgList node) {
    }
    
    public void inAFuncDeclArgType(AFuncDeclArgType node) {
        String varType = node.getIdentifier().getText();
        if (varType.equals("int")) {
            activeFunctionCtx.pendingArgumentType = BasicType.Integer;
        } else if (varType.equals("string")) {
            activeFunctionCtx.pendingArgumentType = BasicType.String;
        } else {
            // TODO LOCATION
            throw new RuntimeException("Unrecognized type in function parameter.");
        }
    }
    
    public void inAFuncDeclArgName(AFuncDeclArgName node) {
        activeFunctionCtx.addVariable(node.getIdentifier(), activeFunctionCtx.pendingArgumentType);
    }
    
    public void outAFuncDeclArgList(AFuncDeclArgList node) {

        activeFunctionCtx.inParameterCount = activeFunctionCtx.getVariableCount();
        
        long argPos = activeFunctionCtx.inParameterCount; 
        while(argPos > 0) {
            argPos--;
            addInstruction(InstructionCode.STACK_TO_VARIABLE, argPos);
        }
    }
    
    
    public void outAFuncDecl(AFuncDecl node) {
        FunctionDefinition newFunc = new FunctionDefinition(activeFunctionCtx.instructions, (int)activeFunctionCtx.getVariableCount(), Arrays.asList(), Arrays.asList(activeFunctionCtx.returnType));
        if (!activeFunctionCtx.hasReturn) {
            throw new RuntimeException("function missing return statement");
        }
        activeFunctionCtx.def = newFunc;
        programCtx.allFunctionCtx.add(activeFunctionCtx);
        activeFunctionCtx = null;
    }
    
    public void inADeclarationStatement(ADeclarationStatement node) {
        declarationStatementCtx = new DeclarationStatementContext();
    }
    
    public void inAVarDeclType(AVarDeclType node) {
        String declaredType = node.getIdentifier().getText();
        if (declaredType.equals("int")) {
            declarationStatementCtx.basicType = BasicType.Integer;
        }
        else if (declaredType.equals("string")) {
            declarationStatementCtx.basicType = BasicType.String;
        } else {
            throw new RuntimeException("int and string are the only valid variable type at the moment.");
        }
    }
    
    public void inAVarDeclName(AVarDeclName node) {
        declarationStatementCtx.varIdentifier = node.getIdentifier();
    }
    
    public void outADeclarationStatement(ADeclarationStatement node) {
        long varPos = activeFunctionCtx.addVariable(declarationStatementCtx.varIdentifier, declarationStatementCtx.basicType);
        
        if (declarationStatementCtx.basicType == BasicType.Integer) {
            addInstruction(InstructionCode.INTEGER_TO_STACK, 0L);
            addInstruction(InstructionCode.STACK_TO_VARIABLE, varPos);
        } else if (declarationStatementCtx.basicType == BasicType.String) {
            addInstruction(InstructionCode.STRING_TO_STACK, "");
            addInstruction(InstructionCode.STACK_TO_VARIABLE, varPos);
        }
        declarationStatementCtx = null;
    }
    
    public void inAAssignmentStatement(AAssignmentStatement node) {
        assignmentStatementCtx = new AssignmentStatementContext();
        pushEtfContext();
    }
    
    public void inAVarAssignName(AVarAssignName node) {
        TIdentifier identifier = node.getIdentifier();
        String varNameToAssign = identifier.getText();
        VariableDefinition varDef = activeFunctionCtx.getVariable(varNameToAssign);
        if (varDef != null) {
            assignmentStatementCtx.varPosition = varDef.variablePosition;
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Assignment to unknown variable or before variable declaration: " + varNameToAssign + " at " + location);
        }
    }
    
    public void outAAssignmentStatement(AAssignmentStatement node) {
        addInstruction(InstructionCode.STACK_TO_VARIABLE, (long)assignmentStatementCtx.varPosition);
        assignmentStatementCtx = null;
        popEtfContext();
    }
    
    public void inAReturnStatement(AReturnStatement node) {
        returnStatementCtx = new ReturnStatementContext();
        pushEtfContext();
    }
    
    public void outAReturnStatement(AReturnStatement node) {
        PExpr exprNode = node.getExpr();
        EtfContext exprCtx = getEtfContext(exprNode);
        boolean isValid = false;
        if (exprCtx.naturalType == BasicType.Integer && activeFunctionCtx.returnType == BaseType.INTEGER) {
            isValid = true;
        } else if (exprCtx.naturalType == BasicType.String && activeFunctionCtx.returnType == BaseType.STRING) {
            isValid = true;
        }

        if (!isValid) {
            // TODO LOCATION
            throw new RuntimeException("Invalid return type");
        }
            
        activeFunctionCtx.hasReturn = true;
        addInstruction(InstructionCode.EXIT_FUNCTION);
        returnStatementCtx = null;
        popEtfContext();
    }
    
    public void outAAddExpr(AAddExpr node) {
        PExpr exprNode = node.getExpr();
        PTerm termNode = node.getTerm();
        
        EtfContext exprCtx = getEtfContext(exprNode);
        EtfContext termCtx = getEtfContext(termNode);
        if (exprCtx.naturalType == BasicType.Integer && termCtx.naturalType == BasicType.Integer) {
            EtfContext currCtx = new EtfContext(BasicType.Integer);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_ADD);
        } else if (exprCtx.naturalType == BasicType.String && termCtx.naturalType == BasicType.String) {
            EtfContext currCtx = new EtfContext(BasicType.String);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.STRING_CONCATENATE);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Addition operation not valid for types: " + exprCtx.naturalType.name() + ", " + termCtx.naturalType.name());
        }
    }

    public void outASubtractExpr(ASubtractExpr node) {
        PExpr exprNode = node.getExpr();
        PTerm termNode = node.getTerm();
        
        EtfContext exprCtx = getEtfContext(exprNode);
        EtfContext termCtx = getEtfContext(termNode);
        if (exprCtx.naturalType == BasicType.Integer && termCtx.naturalType == BasicType.Integer) {
            EtfContext currCtx = new EtfContext(BasicType.Integer);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_SUBTRACT);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Addition operation not valid for types: " + exprCtx.naturalType.name() + ", " + termCtx.naturalType.name());
        }
    }

    public void outAOnlyTermExpr(AOnlyTermExpr node) {
        PTerm termNode = node.getTerm();
        EtfContext termCtx = getEtfContext(termNode);
        
        EtfContext currCtx = new EtfContext(termCtx.naturalType);
        addEtfContext(node, currCtx);
    }
    
    public void outAMultiplyTerm(AMultiplyTerm node) {
        PTerm termNode = node.getTerm();
        PFactor factorNode = node.getFactor();
        
        EtfContext termCtx = getEtfContext(termNode);
        EtfContext factorCtx = getEtfContext(factorNode);
        if (termCtx.naturalType == BasicType.Integer && factorCtx.naturalType == BasicType.Integer) {
            EtfContext currCtx = new EtfContext(BasicType.Integer);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_MULTIPLY);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Addition operation not valid for types: " + termCtx.naturalType.name() + ", " + factorCtx.naturalType.name());
        }
    }

    public void outADivideTerm(ADivideTerm node) {
        PTerm termNode = node.getTerm();
        PFactor factorNode = node.getFactor();
        
        EtfContext termCtx = getEtfContext(termNode);
        EtfContext factorCtx = getEtfContext(factorNode);
        if (termCtx.naturalType == BasicType.Integer && factorCtx.naturalType == BasicType.Integer) {
            EtfContext currCtx = new EtfContext(BasicType.Integer);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_DIVIDE);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Addition operation not valid for types: " + termCtx.naturalType.name() + ", " + factorCtx.naturalType.name());
        }
    }

    public void outAOnlyFactorTerm(AOnlyFactorTerm node) {
        PFactor factorNode = node.getFactor();
        EtfContext factorCtx = getEtfContext(factorNode);
        
        EtfContext currCtx = new EtfContext(factorCtx.naturalType);
        addEtfContext(node, currCtx);
    }
    
    public void inAIntLitFactor(AIntLitFactor node) {
        long val = Long.parseLong(node.getIntString().getText());
        addInstruction(InstructionCode.INTEGER_TO_STACK, val);
    }
    
    public void outAIntLitFactor(AIntLitFactor node) {
        EtfContext currCtx = new EtfContext(BasicType.Integer);
        addEtfContext(node, currCtx);
    }
    
    public void inAStringLitFactor(AStringLitFactor node) {
        String val = node.getQuotedString().getText();
        if (val.length() < 2) {
            throw new RuntimeException("delimited string literals should always have at least 2 chars (for delimiters");
        }
        String valContents = val.substring(1, val.length() - 1);
        addInstruction(InstructionCode.STRING_TO_STACK, valContents);
    }
    
    public void outAStringLitFactor(AStringLitFactor node) {
        EtfContext currCtx = new EtfContext(BasicType.String);
        addEtfContext(node, currCtx);
    }
    
    public void inAFuncCallFactor(AFuncCallFactor node) {
        functionCallCtxStack.push(new FunctionCallContext());
    }
    
    public void outAFuncCallName(AFuncCallName node) {
        functionCallCtxStack.peek().callIdentifier = node.getIdentifier();
    }
    
    public void outAFuncCallArg(AFuncCallArg node) {
        PExpr exprNode = node.getExpr();
        EtfContext exprCtx = getEtfContext(exprNode);
        
        BasicType argType = exprCtx.naturalType;
        functionCallCtxStack.peek().paramTypes.add(argType);
    }
    
    public void outAFuncCallFactor(AFuncCallFactor node) {
        FunctionCallContext currentFunctionCallContext = functionCallCtxStack.pop();
        String funcToCall = currentFunctionCallContext.callIdentifier.getText();
        if (programCtx.localFunctions.containsKey(funcToCall)) {
            long functionNumber = programCtx.localFunctions.get(funcToCall);
            FunctionContext functionCtx = programCtx.allFunctionCtx.get((int)functionNumber);
            
            if (functionCtx.inParameterCount != currentFunctionCallContext.paramTypes.size()) {
                // TODO LOCATION
                throw new RuntimeException("Attempt to call function: number of arguments does not match.");
            }
            
            for (long paramPos = 0; paramPos < functionCtx.inParameterCount; paramPos++) {
                if (functionCtx.getVariable(paramPos).basicType != currentFunctionCallContext.paramTypes.get((int)paramPos)) {
                    // TODO Location
                    throw new RuntimeException("Attempt to call function: argument type does not match.");
                }
            }
                  
            BaseType firstParam = functionCtx.def.getOutParams().get(0);
            BasicType funcType;
            if (firstParam == BaseType.INTEGER) {
                funcType = BasicType.Integer;
            } else if (firstParam == BaseType.STRING) {
                funcType = BasicType.String;
            } else {
                // TODO LOCATION
                throw new RuntimeException("Unknown function return type");
            }
            EtfContext currCtx = new EtfContext(funcType);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.CALL_FUNCTION, functionNumber);
        } else {
            String location = currentFunctionCallContext.callIdentifier.getLine() + ":" + currentFunctionCallContext.callIdentifier.getPos();
            throw new RuntimeException("Unknown function: " + funcToCall + " at " + location);
        }
    }
    
    public void inAVarFetchName(AVarFetchName node) {
        TIdentifier identifier = node.getIdentifier();
        String varNameToFetch = identifier.getText();
        VariableDefinition varDef = activeFunctionCtx.getVariable(varNameToFetch);
        if (varDef != null) {
            long varPos = varDef.variablePosition;
            EtfContext currCtx = new EtfContext(varDef.basicType);
            addEtfContext(node, currCtx);
            addInstruction(InstructionCode.VARIABLE_TO_STACK, varPos);
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Attempt to use unknown variable or before variable declaration: " + varNameToFetch + " at " + location);
        }
    }
    
    public void outAVarFetchFactor(AVarFetchFactor node) {
        PVarFetchName fetchNode = node.getVarFetchName();
        EtfContext fetchCtx = getEtfContext(fetchNode);
        
        EtfContext currCtx = new EtfContext(fetchCtx.naturalType);
        addEtfContext(node, currCtx);
    }
    
    public void outABracketedExprFactor(ABracketedExprFactor node) {
        PExpr exprNode = node.getExpr();
        EtfContext fetchCtx = getEtfContext(exprNode);
        
        EtfContext currCtx = new EtfContext(fetchCtx.naturalType);
        addEtfContext(node, currCtx);
    }
//    public void in(AExpression node) {
//    
//    }
    /**********************  HELPER METHODS  *********************/
    private void pushEtfContext() {
        Map<Node,EtfContext> etfCtx = new HashMap<>();
        etfCtxStack.push(etfCtx);
    }
    
    private Map<Node,EtfContext> popEtfContext() {
        return etfCtxStack.pop();
    }
    
    private EtfContext getEtfContext(Node node) {
        Map<Node,EtfContext> currentMap = etfCtxStack.peek();
        if (currentMap.containsKey(node)) {
            return currentMap.get(node);
        } else {
            return null;
        }
    }
    
    private void addEtfContext(Node node, EtfContext etfContext) {
        Map<Node,EtfContext> currentMap = etfCtxStack.peek();
        currentMap.put(node, etfContext);
    }
    
    private void addInstruction(InstructionCode instructionCode) {
        activeFunctionCtx.instructions.add(new Instruction(instructionCode));
    }
    
    private void addInstruction(InstructionCode instructionCode, Object val) {
        activeFunctionCtx.instructions.add(new Instruction(instructionCode, val));
    }
    
    
}
