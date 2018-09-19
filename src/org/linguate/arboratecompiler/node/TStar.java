/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class TStar extends Token
{
    public TStar()
    {
        super.setText("*");
    }

    public TStar(int line, int pos)
    {
        super.setText("*");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TStar(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTStar(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TStar text.");
    }
}
