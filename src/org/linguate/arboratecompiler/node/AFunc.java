/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AFunc extends PFunc
{
    private PFuncName _funcName_;
    private PExpr _expr_;

    public AFunc()
    {
        // Constructor
    }

    public AFunc(
        @SuppressWarnings("hiding") PFuncName _funcName_,
        @SuppressWarnings("hiding") PExpr _expr_)
    {
        // Constructor
        setFuncName(_funcName_);

        setExpr(_expr_);

    }

    @Override
    public Object clone()
    {
        return new AFunc(
            cloneNode(this._funcName_),
            cloneNode(this._expr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFunc(this);
    }

    public PFuncName getFuncName()
    {
        return this._funcName_;
    }

    public void setFuncName(PFuncName node)
    {
        if(this._funcName_ != null)
        {
            this._funcName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._funcName_ = node;
    }

    public PExpr getExpr()
    {
        return this._expr_;
    }

    public void setExpr(PExpr node)
    {
        if(this._expr_ != null)
        {
            this._expr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._funcName_)
            + toString(this._expr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._funcName_ == child)
        {
            this._funcName_ = null;
            return;
        }

        if(this._expr_ == child)
        {
            this._expr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._funcName_ == oldChild)
        {
            setFuncName((PFuncName) newChild);
            return;
        }

        if(this._expr_ == oldChild)
        {
            setExpr((PExpr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
