/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.analysis;

import org.linguate.arboratecompiler.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAProgram(AProgram node);
    void caseAFuncDecl(AFuncDecl node);
    void caseAFuncArgDecl(AFuncArgDecl node);
    void caseADeclarationStatement(ADeclarationStatement node);
    void caseAAssignmentStatement(AAssignmentStatement node);
    void caseAReturnStatement(AReturnStatement node);
    void caseAFuncCall(AFuncCall node);
    void caseAAddExpr(AAddExpr node);
    void caseASubtractExpr(ASubtractExpr node);
    void caseAMultiplyExpr(AMultiplyExpr node);
    void caseADivideExpr(ADivideExpr node);
    void caseAIntLitValue(AIntLitValue node);
    void caseAFuncCallValue(AFuncCallValue node);
    void caseAVarFetchValue(AVarFetchValue node);
    void caseAFuncDeclName(AFuncDeclName node);
    void caseAFuncArgDeclType(AFuncArgDeclType node);
    void caseAFuncArgDeclName(AFuncArgDeclName node);
    void caseAFuncCallName(AFuncCallName node);
    void caseAVarDeclType(AVarDeclType node);
    void caseAVarDeclName(AVarDeclName node);
    void caseAVarAssignName(AVarAssignName node);
    void caseAVarFetchName(AVarFetchName node);

    void caseTKwFunction(TKwFunction node);
    void caseTKwReturn(TKwReturn node);
    void caseTLeftCurly(TLeftCurly node);
    void caseTRightCurly(TRightCurly node);
    void caseTLeftRound(TLeftRound node);
    void caseTRightRound(TRightRound node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTStar(TStar node);
    void caseTSlash(TSlash node);
    void caseTEquals(TEquals node);
    void caseTComma(TComma node);
    void caseTSemicolon(TSemicolon node);
    void caseTIntString(TIntString node);
    void caseTIdentifier(TIdentifier node);
    void caseTBlank(TBlank node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}
