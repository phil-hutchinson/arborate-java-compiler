/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AStringLitFactor extends PFactor
{
    private TQuotedString _quotedString_;

    public AStringLitFactor()
    {
        // Constructor
    }

    public AStringLitFactor(
        @SuppressWarnings("hiding") TQuotedString _quotedString_)
    {
        // Constructor
        setQuotedString(_quotedString_);

    }

    @Override
    public Object clone()
    {
        return new AStringLitFactor(
            cloneNode(this._quotedString_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStringLitFactor(this);
    }

    public TQuotedString getQuotedString()
    {
        return this._quotedString_;
    }

    public void setQuotedString(TQuotedString node)
    {
        if(this._quotedString_ != null)
        {
            this._quotedString_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._quotedString_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._quotedString_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._quotedString_ == child)
        {
            this._quotedString_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._quotedString_ == oldChild)
        {
            setQuotedString((TQuotedString) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}