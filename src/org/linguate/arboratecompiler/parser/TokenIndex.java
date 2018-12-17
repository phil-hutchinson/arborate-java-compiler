/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.parser;

import org.linguate.arboratecompiler.node.*;
import org.linguate.arboratecompiler.analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    @Override
    public void caseTKwFunction(@SuppressWarnings("unused") TKwFunction node)
    {
        this.index = 0;
    }

    @Override
    public void caseTKwReturn(@SuppressWarnings("unused") TKwReturn node)
    {
        this.index = 1;
    }

    @Override
    public void caseTKwIf(@SuppressWarnings("unused") TKwIf node)
    {
        this.index = 2;
    }

    @Override
    public void caseTLeftCurly(@SuppressWarnings("unused") TLeftCurly node)
    {
        this.index = 3;
    }

    @Override
    public void caseTRightCurly(@SuppressWarnings("unused") TRightCurly node)
    {
        this.index = 4;
    }

    @Override
    public void caseTLeftRound(@SuppressWarnings("unused") TLeftRound node)
    {
        this.index = 5;
    }

    @Override
    public void caseTRightRound(@SuppressWarnings("unused") TRightRound node)
    {
        this.index = 6;
    }

    @Override
    public void caseTPlus(@SuppressWarnings("unused") TPlus node)
    {
        this.index = 7;
    }

    @Override
    public void caseTMinus(@SuppressWarnings("unused") TMinus node)
    {
        this.index = 8;
    }

    @Override
    public void caseTStar(@SuppressWarnings("unused") TStar node)
    {
        this.index = 9;
    }

    @Override
    public void caseTSlash(@SuppressWarnings("unused") TSlash node)
    {
        this.index = 10;
    }

    @Override
    public void caseTLess(@SuppressWarnings("unused") TLess node)
    {
        this.index = 11;
    }

    @Override
    public void caseTGreater(@SuppressWarnings("unused") TGreater node)
    {
        this.index = 12;
    }

    @Override
    public void caseTEquals(@SuppressWarnings("unused") TEquals node)
    {
        this.index = 13;
    }

    @Override
    public void caseTExclamation(@SuppressWarnings("unused") TExclamation node)
    {
        this.index = 14;
    }

    @Override
    public void caseTBar(@SuppressWarnings("unused") TBar node)
    {
        this.index = 15;
    }

    @Override
    public void caseTCaret(@SuppressWarnings("unused") TCaret node)
    {
        this.index = 16;
    }

    @Override
    public void caseTAmpersand(@SuppressWarnings("unused") TAmpersand node)
    {
        this.index = 17;
    }

    @Override
    public void caseTComma(@SuppressWarnings("unused") TComma node)
    {
        this.index = 18;
    }

    @Override
    public void caseTSemicolon(@SuppressWarnings("unused") TSemicolon node)
    {
        this.index = 19;
    }

    @Override
    public void caseTIntString(@SuppressWarnings("unused") TIntString node)
    {
        this.index = 20;
    }

    @Override
    public void caseTQuotedString(@SuppressWarnings("unused") TQuotedString node)
    {
        this.index = 21;
    }

    @Override
    public void caseTBoolString(@SuppressWarnings("unused") TBoolString node)
    {
        this.index = 22;
    }

    @Override
    public void caseTIdentifier(@SuppressWarnings("unused") TIdentifier node)
    {
        this.index = 23;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.index = 24;
    }
}
