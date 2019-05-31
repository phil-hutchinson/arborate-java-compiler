/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class TKwBlock extends Token
{
    public TKwBlock()
    {
        super.setText("block");
    }

    public TKwBlock(int line, int pos)
    {
        super.setText("block");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TKwBlock(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTKwBlock(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TKwBlock text.");
    }
}