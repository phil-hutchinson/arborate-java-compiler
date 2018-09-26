/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.analysis;

import java.util.*;
import org.linguate.arboratecompiler.node.*;

public class ReversedDepthFirstAdapter extends AnalysisAdapter
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
        node.getEOF().apply(this);
        node.getPFunc().apply(this);
        outStart(node);
    }

    public void inAFunc(AFunc node)
    {
        defaultIn(node);
    }

    public void outAFunc(AFunc node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFunc(AFunc node)
    {
        inAFunc(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        outAFunc(node);
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
        if(node.getOp2() != null)
        {
            node.getOp2().apply(this);
        }
        if(node.getOp1() != null)
        {
            node.getOp1().apply(this);
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
        if(node.getOp2() != null)
        {
            node.getOp2().apply(this);
        }
        if(node.getOp1() != null)
        {
            node.getOp1().apply(this);
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
        if(node.getOp2() != null)
        {
            node.getOp2().apply(this);
        }
        if(node.getOp1() != null)
        {
            node.getOp1().apply(this);
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
        if(node.getOp2() != null)
        {
            node.getOp2().apply(this);
        }
        if(node.getOp1() != null)
        {
            node.getOp1().apply(this);
        }
        outADivideExpr(node);
    }

    public void inAIntLit(AIntLit node)
    {
        defaultIn(node);
    }

    public void outAIntLit(AIntLit node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntLit(AIntLit node)
    {
        inAIntLit(node);
        if(node.getIntString() != null)
        {
            node.getIntString().apply(this);
        }
        outAIntLit(node);
    }
}
