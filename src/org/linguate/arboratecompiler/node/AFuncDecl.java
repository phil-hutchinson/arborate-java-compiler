/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import java.util.*;
import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AFuncDecl extends PFuncDecl
{
    private PFuncDeclName _funcDeclName_;
    private final LinkedList<PFuncDeclArg> _funcDeclArg_ = new LinkedList<PFuncDeclArg>();
    private final LinkedList<PStatement> _statement_ = new LinkedList<PStatement>();

    public AFuncDecl()
    {
        // Constructor
    }

    public AFuncDecl(
        @SuppressWarnings("hiding") PFuncDeclName _funcDeclName_,
        @SuppressWarnings("hiding") List<?> _funcDeclArg_,
        @SuppressWarnings("hiding") List<?> _statement_)
    {
        // Constructor
        setFuncDeclName(_funcDeclName_);

        setFuncDeclArg(_funcDeclArg_);

        setStatement(_statement_);

    }

    @Override
    public Object clone()
    {
        return new AFuncDecl(
            cloneNode(this._funcDeclName_),
            cloneList(this._funcDeclArg_),
            cloneList(this._statement_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFuncDecl(this);
    }

    public PFuncDeclName getFuncDeclName()
    {
        return this._funcDeclName_;
    }

    public void setFuncDeclName(PFuncDeclName node)
    {
        if(this._funcDeclName_ != null)
        {
            this._funcDeclName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._funcDeclName_ = node;
    }

    public LinkedList<PFuncDeclArg> getFuncDeclArg()
    {
        return this._funcDeclArg_;
    }

    public void setFuncDeclArg(List<?> list)
    {
        for(PFuncDeclArg e : this._funcDeclArg_)
        {
            e.parent(null);
        }
        this._funcDeclArg_.clear();

        for(Object obj_e : list)
        {
            PFuncDeclArg e = (PFuncDeclArg) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._funcDeclArg_.add(e);
        }
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
            + toString(this._funcDeclName_)
            + toString(this._funcDeclArg_)
            + toString(this._statement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._funcDeclName_ == child)
        {
            this._funcDeclName_ = null;
            return;
        }

        if(this._funcDeclArg_.remove(child))
        {
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
        if(this._funcDeclName_ == oldChild)
        {
            setFuncDeclName((PFuncDeclName) newChild);
            return;
        }

        for(ListIterator<PFuncDeclArg> i = this._funcDeclArg_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PFuncDeclArg) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
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
