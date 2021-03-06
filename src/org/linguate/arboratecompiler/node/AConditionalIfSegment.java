/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import java.util.*;
import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AConditionalIfSegment extends PIfSegment
{
    private PIfCondition _ifCondition_;
    private final LinkedList<PStatement> _statement_ = new LinkedList<PStatement>();

    public AConditionalIfSegment()
    {
        // Constructor
    }

    public AConditionalIfSegment(
        @SuppressWarnings("hiding") PIfCondition _ifCondition_,
        @SuppressWarnings("hiding") List<?> _statement_)
    {
        // Constructor
        setIfCondition(_ifCondition_);

        setStatement(_statement_);

    }

    @Override
    public Object clone()
    {
        return new AConditionalIfSegment(
            cloneNode(this._ifCondition_),
            cloneList(this._statement_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConditionalIfSegment(this);
    }

    public PIfCondition getIfCondition()
    {
        return this._ifCondition_;
    }

    public void setIfCondition(PIfCondition node)
    {
        if(this._ifCondition_ != null)
        {
            this._ifCondition_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ifCondition_ = node;
    }

    public LinkedList<PStatement> getStatement()
    {
        return this._statement_;
    }

    public void setStatement(List<?> list)
    {
        for(PStatement e : this._statement_)
        {
            e.parent(null);
        }
        this._statement_.clear();

        for(Object obj_e : list)
        {
            PStatement e = (PStatement) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._statement_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._ifCondition_)
            + toString(this._statement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._ifCondition_ == child)
        {
            this._ifCondition_ = null;
            return;
        }

        if(this._statement_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._ifCondition_ == oldChild)
        {
            setIfCondition((PIfCondition) newChild);
            return;
        }

        for(ListIterator<PStatement> i = this._statement_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PStatement) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
