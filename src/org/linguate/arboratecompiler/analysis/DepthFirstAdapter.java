/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.analysis;

import java.util.*;
import org.linguate.arboratecompiler.node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    public void defaultOut(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node)
    {
        inStart(node);
        node.getPProgram().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAProgram(AProgram node)
    {
        defaultIn(node);
    }

    public void outAProgram(AProgram node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProgram(AProgram node)
    {
        inAProgram(node);
        {
            List<PFuncDecl> copy = new ArrayList<PFuncDecl>(node.getFunctions());
            for(PFuncDecl e : copy)
            {
                e.apply(this);
            }
        }
        outAProgram(node);
    }

    public void inAFuncDecl(AFuncDecl node)
    {
        defaultIn(node);
    }

    public void outAFuncDecl(AFuncDecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDecl(AFuncDecl node)
    {
        inAFuncDecl(node);
        if(node.getFuncDeclRetType() != null)
        {
            node.getFuncDeclRetType().apply(this);
        }
        if(node.getFuncDeclName() != null)
        {
            node.getFuncDeclName().apply(this);
        }
        if(node.getFuncDeclArgList() != null)
        {
            node.getFuncDeclArgList().apply(this);
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getStatement());
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
        outAFuncDecl(node);
    }

    public void inAFuncDeclArgList(AFuncDeclArgList node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclArgList(AFuncDeclArgList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclArgList(AFuncDeclArgList node)
    {
        inAFuncDeclArgList(node);
        {
            List<PFuncDeclArg> copy = new ArrayList<PFuncDeclArg>(node.getFuncDeclArg());
            for(PFuncDeclArg e : copy)
            {
                e.apply(this);
            }
        }
        outAFuncDeclArgList(node);
    }

    public void inAFuncDeclArg(AFuncDeclArg node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclArg(AFuncDeclArg node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclArg(AFuncDeclArg node)
    {
        inAFuncDeclArg(node);
        if(node.getFuncDeclArgType() != null)
        {
            node.getFuncDeclArgType().apply(this);
        }
        if(node.getFuncDeclArgName() != null)
        {
            node.getFuncDeclArgName().apply(this);
        }
        outAFuncDeclArg(node);
    }

    public void inADeclarationStatement(ADeclarationStatement node)
    {
        defaultIn(node);
    }

    public void outADeclarationStatement(ADeclarationStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADeclarationStatement(ADeclarationStatement node)
    {
        inADeclarationStatement(node);
        if(node.getVarDeclType() != null)
        {
            node.getVarDeclType().apply(this);
        }
        if(node.getVarDeclName() != null)
        {
            node.getVarDeclName().apply(this);
        }
        outADeclarationStatement(node);
    }

    public void inAAssignmentStatement(AAssignmentStatement node)
    {
        defaultIn(node);
    }

    public void outAAssignmentStatement(AAssignmentStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAssignmentStatement(AAssignmentStatement node)
    {
        inAAssignmentStatement(node);
        if(node.getVarAssignName() != null)
        {
            node.getVarAssignName().apply(this);
        }
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        outAAssignmentStatement(node);
    }

    public void inAReturnStatement(AReturnStatement node)
    {
        defaultIn(node);
    }

    public void outAReturnStatement(AReturnStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReturnStatement(AReturnStatement node)
    {
        inAReturnStatement(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        outAReturnStatement(node);
    }

    public void inAEqualExpr(AEqualExpr node)
    {
        defaultIn(node);
    }

    public void outAEqualExpr(AEqualExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEqualExpr(AEqualExpr node)
    {
        inAEqualExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAEqualExpr(node);
    }

    public void inANotEqualExpr(ANotEqualExpr node)
    {
        defaultIn(node);
    }

    public void outANotEqualExpr(ANotEqualExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANotEqualExpr(ANotEqualExpr node)
    {
        inANotEqualExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outANotEqualExpr(node);
    }

    public void inALessThanExpr(ALessThanExpr node)
    {
        defaultIn(node);
    }

    public void outALessThanExpr(ALessThanExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALessThanExpr(ALessThanExpr node)
    {
        inALessThanExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outALessThanExpr(node);
    }

    public void inAGreaterThanExpr(AGreaterThanExpr node)
    {
        defaultIn(node);
    }

    public void outAGreaterThanExpr(AGreaterThanExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGreaterThanExpr(AGreaterThanExpr node)
    {
        inAGreaterThanExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAGreaterThanExpr(node);
    }

    public void inALessEqualExpr(ALessEqualExpr node)
    {
        defaultIn(node);
    }

    public void outALessEqualExpr(ALessEqualExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALessEqualExpr(ALessEqualExpr node)
    {
        inALessEqualExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outALessEqualExpr(node);
    }

    public void inAGreaterEqualExpr(AGreaterEqualExpr node)
    {
        defaultIn(node);
    }

    public void outAGreaterEqualExpr(AGreaterEqualExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGreaterEqualExpr(AGreaterEqualExpr node)
    {
        inAGreaterEqualExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAGreaterEqualExpr(node);
    }

    public void inAAddExpr(AAddExpr node)
    {
        defaultIn(node);
    }

    public void outAAddExpr(AAddExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAddExpr(AAddExpr node)
    {
        inAAddExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAAddExpr(node);
    }

    public void inASubtractExpr(ASubtractExpr node)
    {
        defaultIn(node);
    }

    public void outASubtractExpr(ASubtractExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASubtractExpr(ASubtractExpr node)
    {
        inASubtractExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outASubtractExpr(node);
    }

    public void inAMultiplyExpr(AMultiplyExpr node)
    {
        defaultIn(node);
    }

    public void outAMultiplyExpr(AMultiplyExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMultiplyExpr(AMultiplyExpr node)
    {
        inAMultiplyExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAMultiplyExpr(node);
    }

    public void inADivideExpr(ADivideExpr node)
    {
        defaultIn(node);
    }

    public void outADivideExpr(ADivideExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADivideExpr(ADivideExpr node)
    {
        inADivideExpr(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outADivideExpr(node);
    }

    public void inAIntLitExpr(AIntLitExpr node)
    {
        defaultIn(node);
    }

    public void outAIntLitExpr(AIntLitExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntLitExpr(AIntLitExpr node)
    {
        inAIntLitExpr(node);
        if(node.getIntString() != null)
        {
            node.getIntString().apply(this);
        }
        outAIntLitExpr(node);
    }

    public void inAStringLitExpr(AStringLitExpr node)
    {
        defaultIn(node);
    }

    public void outAStringLitExpr(AStringLitExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAStringLitExpr(AStringLitExpr node)
    {
        inAStringLitExpr(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outAStringLitExpr(node);
    }

    public void inAFuncCallExpr(AFuncCallExpr node)
    {
        defaultIn(node);
    }

    public void outAFuncCallExpr(AFuncCallExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncCallExpr(AFuncCallExpr node)
    {
        inAFuncCallExpr(node);
        if(node.getFuncCallName() != null)
        {
            node.getFuncCallName().apply(this);
        }
        if(node.getFuncCallArgList() != null)
        {
            node.getFuncCallArgList().apply(this);
        }
        outAFuncCallExpr(node);
    }

    public void inAVarFetchExpr(AVarFetchExpr node)
    {
        defaultIn(node);
    }

    public void outAVarFetchExpr(AVarFetchExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarFetchExpr(AVarFetchExpr node)
    {
        inAVarFetchExpr(node);
        if(node.getVarFetchName() != null)
        {
            node.getVarFetchName().apply(this);
        }
        outAVarFetchExpr(node);
    }

    public void inAFuncCallArgList(AFuncCallArgList node)
    {
        defaultIn(node);
    }

    public void outAFuncCallArgList(AFuncCallArgList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncCallArgList(AFuncCallArgList node)
    {
        inAFuncCallArgList(node);
        {
            List<PFuncCallArg> copy = new ArrayList<PFuncCallArg>(node.getFuncCallArg());
            for(PFuncCallArg e : copy)
            {
                e.apply(this);
            }
        }
        outAFuncCallArgList(node);
    }

    public void inAFuncCallArg(AFuncCallArg node)
    {
        defaultIn(node);
    }

    public void outAFuncCallArg(AFuncCallArg node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncCallArg(AFuncCallArg node)
    {
        inAFuncCallArg(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        outAFuncCallArg(node);
    }

    public void inAFuncDeclRetType(AFuncDeclRetType node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclRetType(AFuncDeclRetType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclRetType(AFuncDeclRetType node)
    {
        inAFuncDeclRetType(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAFuncDeclRetType(node);
    }

    public void inAFuncDeclName(AFuncDeclName node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclName(AFuncDeclName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclName(AFuncDeclName node)
    {
        inAFuncDeclName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAFuncDeclName(node);
    }

    public void inAFuncDeclArgType(AFuncDeclArgType node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclArgType(AFuncDeclArgType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclArgType(AFuncDeclArgType node)
    {
        inAFuncDeclArgType(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAFuncDeclArgType(node);
    }

    public void inAFuncDeclArgName(AFuncDeclArgName node)
    {
        defaultIn(node);
    }

    public void outAFuncDeclArgName(AFuncDeclArgName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncDeclArgName(AFuncDeclArgName node)
    {
        inAFuncDeclArgName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAFuncDeclArgName(node);
    }

    public void inAFuncCallName(AFuncCallName node)
    {
        defaultIn(node);
    }

    public void outAFuncCallName(AFuncCallName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFuncCallName(AFuncCallName node)
    {
        inAFuncCallName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAFuncCallName(node);
    }

    public void inAVarDeclType(AVarDeclType node)
    {
        defaultIn(node);
    }

    public void outAVarDeclType(AVarDeclType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarDeclType(AVarDeclType node)
    {
        inAVarDeclType(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAVarDeclType(node);
    }

    public void inAVarDeclName(AVarDeclName node)
    {
        defaultIn(node);
    }

    public void outAVarDeclName(AVarDeclName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarDeclName(AVarDeclName node)
    {
        inAVarDeclName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAVarDeclName(node);
    }

    public void inAVarAssignName(AVarAssignName node)
    {
        defaultIn(node);
    }

    public void outAVarAssignName(AVarAssignName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarAssignName(AVarAssignName node)
    {
        inAVarAssignName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAVarAssignName(node);
    }

    public void inAVarFetchName(AVarFetchName node)
    {
        defaultIn(node);
    }

    public void outAVarFetchName(AVarFetchName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarFetchName(AVarFetchName node)
    {
        inAVarFetchName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAVarFetchName(node);
    }
}
