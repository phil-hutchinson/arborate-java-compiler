/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class TKwReturn extends Token
{
    public TKwReturn()
    {
        super.setText("return");
    }

    public TKwReturn(int line, int pos)
    {
        super.setText("return");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwReturn(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwReturn(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwReturn text.");
    }
}
