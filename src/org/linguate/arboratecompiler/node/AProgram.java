/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import java.util.*;
import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class AProgram extends PProgram
{
    private final LinkedList<PFunc> _functions_ = new LinkedList<PFunc>();

    public AProgram()
    {
        // Constructor
    }

    public AProgram(
        @SuppressWarnings("hiding") List<?> _functions_)
    {
        // Constructor
        setFunctions(_functions_);

    }

    @Override
    public Object clone()
    {
        return new AProgram(
            cloneList(this._functions_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAProgram(this);
    }

    public LinkedList<PFunc> getFunctions()
    {
        return this._functions_;
    }

    public void setFunctions(List<?> list)
    {
        for(PFunc e : this._functions_)
        {
            e.parent(null);
        }
        this._functions_.clear();

        for(Object obj_e : list)
        {
            PFunc e = (PFunc) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._functions_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._functions_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._functions_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PFunc> i = this._functions_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PFunc) newChild);
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
