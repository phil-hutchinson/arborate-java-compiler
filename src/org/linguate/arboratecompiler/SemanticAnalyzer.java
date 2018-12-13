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
    Stack<Map<Node,ExpressionContext>> expressionCtxStack = new Stack<>();
            
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
        } else if (returnType.equals("boolean")) {
            activeFunctionCtx.returnType = BaseType.BOOLEAN;
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
        } else if (varType.equals("boolean")) {
            activeFunctionCtx.pendingArgumentType = BasicType.Boolean;
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Unrecognized type in function parameter: " + varType);
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
        } else if (declaredType.equals("string")) {
            declarationStatementCtx.basicType = BasicType.String;
        } else if (declaredType.equals("boolean")) {
            declarationStatementCtx.basicType = BasicType.Boolean;
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Unrecognized variable type: " + declaredType);
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
        } else if (declarationStatementCtx.basicType == BasicType.Boolean) {
            addInstruction(InstructionCode.BOOLEAN_TO_STACK, false);
            addInstruction(InstructionCode.STACK_TO_VARIABLE, varPos);
        } else {
            // TODO UNEXPECTED ERROR
            throw new RuntimeException("Unrecognized type initializing stack for declaration statement: " + declarationStatementCtx.basicType.toString());
        }
        declarationStatementCtx = null;
    }
    
    public void inAAssignmentStatement(AAssignmentStatement node) {
        assignmentStatementCtx = new AssignmentStatementContext();
        pushExpressionContextStack();
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
        popExpressionContextStack();
    }
    
    public void inAReturnStatement(AReturnStatement node) {
        returnStatementCtx = new ReturnStatementContext();
        pushExpressionContextStack();
    }
    
    public void outAReturnStatement(AReturnStatement node) {
        PExpr exprNode = node.getExpr();
        ExpressionContext exprCtx = getExpressionContext(exprNode);
        boolean isValid = false;
        if (exprCtx.naturalType == BasicType.Integer && activeFunctionCtx.returnType == BaseType.INTEGER) {
            isValid = true;
        } else if (exprCtx.naturalType == BasicType.String && activeFunctionCtx.returnType == BaseType.STRING) {
            isValid = true;
        } else if (exprCtx.naturalType == BasicType.Boolean && activeFunctionCtx.returnType == BaseType.BOOLEAN) {
            isValid = true;
        }

        if (!isValid) {
            // TODO ERRORLOCATION
            throw new RuntimeException("Invalid return type");
        }
            
        activeFunctionCtx.hasReturn = true;
        addInstruction(InstructionCode.EXIT_FUNCTION);
        returnStatementCtx = null;
        popExpressionContextStack();
    }
    
    public void outAAddExpr(AAddExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Integer);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_ADD);
        } else if (leftCtx.naturalType == BasicType.String && rightCtx.naturalType == BasicType.String) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.String);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.STRING_CONCATENATE);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Addition operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }

    public void outASubtractExpr(ASubtractExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Integer);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_SUBTRACT);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Subtraction operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }

    public void outAMultiplyExpr(AMultiplyExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Integer);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_MULTIPLY);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Multiplication operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }

    public void outADivideExpr(ADivideExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Integer);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_DIVIDE);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Division operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }

    public void outAEqualExpr(AEqualExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == rightCtx.naturalType) {
            addExpressionContext(node, new ExpressionContext(BasicType.Boolean));
            switch(leftCtx.naturalType) {
                case Integer:
                    addInstruction(InstructionCode.INTEGER_EQUAL);
                    break;
                    
                case String:
                    addInstruction(InstructionCode.STRING_EQUAL);
                    break; 
                    
                default:
                    // TODO ERRORLOCATION
                    throw new RuntimeException("Unknown type in equal comparison: " + leftCtx.naturalType.name());
            }
            
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Non-matching types in equal comparison: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }
    
    public void outANotEqualExpr(ANotEqualExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == rightCtx.naturalType) {
            addExpressionContext(node, new ExpressionContext(BasicType.Boolean));
            switch(leftCtx.naturalType) {
                case Integer:
                    addInstruction(InstructionCode.INTEGER_NOT_EQUAL);
                    break;
                    
                case String:
                    addInstruction(InstructionCode.STRING_NOT_EQUAL);
                    break; 
                    
                default:
                    // TODO ERRORLOCATION
                    throw new RuntimeException("Unknown type in not equal comparison: " + leftCtx.naturalType.name());
            }
            
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Non-matching types in not equal comparison: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }

    public void outALessThanExpr(ALessThanExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Boolean);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_LESS_THAN);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Less Than operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }
    
    public void outALessEqualExpr(ALessEqualExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Boolean);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_LESS_EQUAL);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Less Than Or Equal operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }
    
    public void outAGreaterThanExpr(AGreaterThanExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Boolean);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_GREATER_THAN);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Greater Than operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }
    
    public void outAGreaterEqualExpr(AGreaterEqualExpr node) {
        PExpr leftNode = node.getLeft();
        PExpr rightNode = node.getRight();
        
        ExpressionContext leftCtx = getExpressionContext(leftNode);
        ExpressionContext rightCtx = getExpressionContext(rightNode);
        if (leftCtx.naturalType == BasicType.Integer && rightCtx.naturalType == BasicType.Integer) {
            ExpressionContext currCtx = new ExpressionContext(BasicType.Boolean);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.INTEGER_GREATER_EQUAL);
        } else {
            // TODO ERRORLOCATION
            throw new RuntimeException("Greater Than Or Equal operation not valid for types: " + leftCtx.naturalType.name() + ", " + rightCtx.naturalType.name());
        }
    }
    
    public void inAIntLitExpr(AIntLitExpr node) {
        long val = Long.parseLong(node.getIntString().getText());
        addInstruction(InstructionCode.INTEGER_TO_STACK, val);
    }
    
    public void outAIntLitExpr(AIntLitExpr node) {
        ExpressionContext currCtx = new ExpressionContext(BasicType.Integer);
        addExpressionContext(node, currCtx);
    }
    
    public void inAStringLitExpr(AStringLitExpr node) {
        String val = node.getQuotedString().getText();
        if (val.length() < 2) {
            throw new RuntimeException("delimited string literals should always have at least 2 chars (for delimiters");
        }
        String valContents = val.substring(1, val.length() - 1);
        addInstruction(InstructionCode.STRING_TO_STACK, valContents);
    }
    
    public void outAStringLitExpr(AStringLitExpr node) {
        ExpressionContext currCtx = new ExpressionContext(BasicType.String);
        addExpressionContext(node, currCtx);
    }
    
    public void inABoolLitExpr(ABoolLitExpr node) {
        switch (node.getBoolString().getText()) {
            case "true":
                addInstruction(InstructionCode.BOOLEAN_TO_STACK, true);
                break;

            case "false":
                addInstruction(InstructionCode.BOOLEAN_TO_STACK, false);
                break;
                
            default:
                // unexpected error
                throw new RuntimeException("Unexpected boolean literal");
        }
    }
    
    public void outABoolLitExpr(ABoolLitExpr node) {
        ExpressionContext currCtx = new ExpressionContext(BasicType.Boolean);
        addExpressionContext(node, currCtx);
    }
    
    public void inAFuncCallExpr(AFuncCallExpr node) {
        functionCallCtxStack.push(new FunctionCallContext());
    }
    
    public void outAFuncCallName(AFuncCallName node) {
        functionCallCtxStack.peek().callIdentifier = node.getIdentifier();
    }
    
    public void outAFuncCallArg(AFuncCallArg node) {
        PExpr exprNode = node.getExpr();
        ExpressionContext exprCtx = getExpressionContext(exprNode);
        
        BasicType argType = exprCtx.naturalType;
        functionCallCtxStack.peek().paramTypes.add(argType);
    }
    
    public void outAFuncCallExpr(AFuncCallExpr node) {
        FunctionCallContext currentFunctionCallContext = functionCallCtxStack.pop();
        String funcToCall = currentFunctionCallContext.callIdentifier.getText();
        if (programCtx.localFunctions.containsKey(funcToCall)) {
            long functionNumber = programCtx.localFunctions.get(funcToCall);
            FunctionContext functionCtx = programCtx.allFunctionCtx.get((int)functionNumber);
            
            if (functionCtx.inParameterCount != currentFunctionCallContext.paramTypes.size()) {
                // TODO ERRORLOCATION
                throw new RuntimeException("Attempt to call function: number of arguments does not match.");
            }
            
            for (long paramPos = 0; paramPos < functionCtx.inParameterCount; paramPos++) {
                if (functionCtx.getVariable(paramPos).basicType != currentFunctionCallContext.paramTypes.get((int)paramPos)) {
                    // TODO ERRORLOCATION
                    throw new RuntimeException("Attempt to call function: argument type does not match.");
                }
            }
                  
            BaseType firstParam = functionCtx.def.getOutParams().get(0);
            BasicType funcType;
            if (firstParam == BaseType.INTEGER) {
                funcType = BasicType.Integer;
            } else if (firstParam == BaseType.STRING) {
                funcType = BasicType.String;
            } else if (firstParam == BaseType.BOOLEAN) {
                funcType = BasicType.Boolean;
            } else {
                // TODO ERRORLOCATION
                throw new RuntimeException("Unknown function return type: " + firstParam);
            }
            ExpressionContext currCtx = new ExpressionContext(funcType);
            addExpressionContext(node, currCtx);
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
            ExpressionContext currCtx = new ExpressionContext(varDef.basicType);
            addExpressionContext(node, currCtx);
            addInstruction(InstructionCode.VARIABLE_TO_STACK, varPos);
        } else {
            String location = identifier.getLine() + ":" + identifier.getPos();
            throw new RuntimeException("Attempt to use unknown variable or before variable declaration: " + varNameToFetch + " at " + location);
        }
    }
    
    public void outAVarFetchExpr(AVarFetchExpr node) {
        PVarFetchName fetchNode = node.getVarFetchName();
        ExpressionContext fetchCtx = getExpressionContext(fetchNode);
        
        ExpressionContext currCtx = new ExpressionContext(fetchCtx.naturalType);
        addExpressionContext(node, currCtx);
    }
    
    /**********************  HELPER METHODS  *********************/
    private void pushExpressionContextStack() {
        Map<Node,ExpressionContext> expressionCtx = new HashMap<>();
        expressionCtxStack.push(expressionCtx);
    }
    
    private Map<Node,ExpressionContext> popExpressionContextStack() {
        return expressionCtxStack.pop();
    }
    
    private ExpressionContext getExpressionContext(Node node) {
        Map<Node,ExpressionContext> currentMap = expressionCtxStack.peek();
        if (currentMap.containsKey(node)) {
            return currentMap.get(node);
        } else {
            return null;
        }
    }
    
    private void addExpressionContext(Node node, ExpressionContext expressionContext) {
        Map<Node,ExpressionContext> currentMap = expressionCtxStack.peek();
        currentMap.put(node, expressionContext);
    }
    
    private void addInstruction(InstructionCode instructionCode) {
        activeFunctionCtx.instructions.add(new Instruction(instructionCode));
    }
    
    private void addInstruction(InstructionCode instructionCode, Object val) {
        activeFunctionCtx.instructions.add(new Instruction(instructionCode, val));
    }
    
    
}
