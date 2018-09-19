/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AIntLit extends PIntLit
{
    private TIntString _intString_;

    public AIntLit()
    {
        // Constructor
    }

    public AIntLit(
        @SuppressWarnings("hiding") TIntString _intString_)
    {
        // Constructor
        setIntString(_intString_);

    }

    @Override
    public Object clone()
    {
        return new AIntLit(
            cloneNode(this._intString_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIntLit(this);
    }

    public TIntString getIntString()
    {
        return this._intString_;
    }

    public void setIntString(TIntString node)
    {
        if(this._intString_ != null)
        {
            this._intString_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._intString_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._intString_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._intString_ == child)
        {
            this._intString_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._intString_ == oldChild)
        {
            setIntString((TIntString) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
