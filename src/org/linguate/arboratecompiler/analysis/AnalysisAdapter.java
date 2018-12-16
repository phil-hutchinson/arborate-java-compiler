/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.analysis;

import java.util.*;
import org.linguate.arboratecompiler.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAProgram(AProgram node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDecl(AFuncDecl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclArgList(AFuncDeclArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclArg(AFuncDeclArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACodeBlockStatement(ACodeBlockStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADeclarationStatement(ADeclarationStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAssignmentStatement(AAssignmentStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReturnStatement(AReturnStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALogicalOrExpr(ALogicalOrExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALogicalXorExpr(ALogicalXorExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALogicalAndExpr(ALogicalAndExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEqualExpr(AEqualExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotEqualExpr(ANotEqualExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALessThanExpr(ALessThanExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGreaterThanExpr(AGreaterThanExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALessEqualExpr(ALessEqualExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGreaterEqualExpr(AGreaterEqualExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAddExpr(AAddExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASubtractExpr(ASubtractExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiplyExpr(AMultiplyExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivideExpr(ADivideExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALogicalNotExpr(ALogicalNotExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntLitExpr(AIntLitExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringLitExpr(AStringLitExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABoolLitExpr(ABoolLitExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncCallExpr(AFuncCallExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarFetchExpr(AVarFetchExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncCallArgList(AFuncCallArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncCallArg(AFuncCallArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclRetType(AFuncDeclRetType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclName(AFuncDeclName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclArgType(AFuncDeclArgType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncDeclArgName(AFuncDeclArgName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncCallName(AFuncCallName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarDeclType(AVarDeclType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarDeclName(AVarDeclName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarAssignName(AVarAssignName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarFetchName(AVarFetchName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTKwFunction(TKwFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTKwReturn(TKwReturn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLeftCurly(TLeftCurly node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRightCurly(TRightCurly node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLeftRound(TLeftRound node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRightRound(TRightRound node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStar(TStar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSlash(TSlash node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLess(TLess node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGreater(TGreater node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEquals(TEquals node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTExclamation(TExclamation node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBar(TBar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCaret(TCaret node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAmpersand(TAmpersand node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntString(TIntString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTQuotedString(TQuotedString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolString(TBoolString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBlank(TBlank node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    @Override
    public void caseInvalidToken(InvalidToken node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
