/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class TKwElseif extends Token
{
    public TKwElseif()
    {
        super.setText("elseif");
    }

    public TKwElseif(int line, int pos)
    {
        super.setText("elseif");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwElseif(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwElseif(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwElseif text.");
    }
}
