/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AFuncCallValue extends PValue
{
    private PFuncCallName _funcCallName_;
    private PFuncCallArgList _funcCallArgList_;

    public AFuncCallValue()
    {
        // Constructor
    }

    public AFuncCallValue(
        @SuppressWarnings("hiding") PFuncCallName _funcCallName_,
        @SuppressWarnings("hiding") PFuncCallArgList _funcCallArgList_)
    {
        // Constructor
        setFuncCallName(_funcCallName_);

        setFuncCallArgList(_funcCallArgList_);

    }

    @Override
    public Object clone()
    {
        return new AFuncCallValue(
            cloneNode(this._funcCallName_),
            cloneNode(this._funcCallArgList_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFuncCallValue(this);
    }

    public PFuncCallName getFuncCallName()
    {
        return this._funcCallName_;
    }

    public void setFuncCallName(PFuncCallName node)
    {
        if(this._funcCallName_ != null)
        {
            this._funcCallName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._funcCallName_ = node;
    }

    public PFuncCallArgList getFuncCallArgList()
    {
        return this._funcCallArgList_;
    }

    public void setFuncCallArgList(PFuncCallArgList node)
    {
        if(this._funcCallArgList_ != null)
        {
            this._funcCallArgList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._funcCallArgList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._funcCallName_)
            + toString(this._funcCallArgList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._funcCallName_ == child)
        {
            this._funcCallName_ = null;
            return;
        }

        if(this._funcCallArgList_ == child)
        {
            this._funcCallArgList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._funcCallName_ == oldChild)
        {
            setFuncCallName((PFuncCallName) newChild);
            return;
        }

        if(this._funcCallArgList_ == oldChild)
        {
            setFuncCallArgList((PFuncCallArgList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
