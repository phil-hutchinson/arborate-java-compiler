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
    void caseAFuncDeclArgList(AFuncDeclArgList node);
    void caseAFuncDeclArg(AFuncDeclArg node);
    void caseADeclarationStatement(ADeclarationStatement node);
    void caseAAssignmentStatement(AAssignmentStatement node);
    void caseAReturnStatement(AReturnStatement node);
    void caseAAddExpr(AAddExpr node);
    void caseASubtractExpr(ASubtractExpr node);
    void caseAOnlyTermExpr(AOnlyTermExpr node);
    void caseAMultiplyTerm(AMultiplyTerm node);
    void caseADivideTerm(ADivideTerm node);
    void caseAOnlyFactorTerm(AOnlyFactorTerm node);
    void caseAIntLitFactor(AIntLitFactor node);
    void caseAFuncCallFactor(AFuncCallFactor node);
    void caseAVarFetchFactor(AVarFetchFactor node);
    void caseABracketedExprFactor(ABracketedExprFactor node);
    void caseAFuncCallArgList(AFuncCallArgList node);
    void caseAFuncCallArg(AFuncCallArg node);
    void caseAFuncDeclName(AFuncDeclName node);
    void caseAFuncDeclArgType(AFuncDeclArgType node);
    void caseAFuncDeclArgName(AFuncDeclArgName node);
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
