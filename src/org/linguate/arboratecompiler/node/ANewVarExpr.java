/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class ANewVarExpr extends PExpr
{
    private TIdentifier _newVarType_;
    private PNewVarInitFieldList _newVarInitFieldList_;

    public ANewVarExpr()
    {
        // Constructor
    }

    public ANewVarExpr(
        @SuppressWarnings("hiding") TIdentifier _newVarType_,
        @SuppressWarnings("hiding") PNewVarInitFieldList _newVarInitFieldList_)
    {
        // Constructor
        setNewVarType(_newVarType_);

        setNewVarInitFieldList(_newVarInitFieldList_);

    }

    @Override
    public Object clone()
    {
        return new ANewVarExpr(
            cloneNode(this._newVarType_),
            cloneNode(this._newVarInitFieldList_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANewVarExpr(this);
    }

    public TIdentifier getNewVarType()
    {
        return this._newVarType_;
    }

    public void setNewVarType(TIdentifier node)
    {
        if(this._newVarType_ != null)
        {
            this._newVarType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._newVarType_ = node;
    }

    public PNewVarInitFieldList getNewVarInitFieldList()
    {
        return this._newVarInitFieldList_;
    }

    public void setNewVarInitFieldList(PNewVarInitFieldList node)
    {
        if(this._newVarInitFieldList_ != null)
        {
            this._newVarInitFieldList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._newVarInitFieldList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._newVarType_)
            + toString(this._newVarInitFieldList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._newVarType_ == child)
        {
            this._newVarType_ = null;
            return;
        }

        if(this._newVarInitFieldList_ == child)
        {
            this._newVarInitFieldList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._newVarType_ == oldChild)
        {
            setNewVarType((TIdentifier) newChild);
            return;
        }

        if(this._newVarInitFieldList_ == oldChild)
        {
            setNewVarInitFieldList((PNewVarInitFieldList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
