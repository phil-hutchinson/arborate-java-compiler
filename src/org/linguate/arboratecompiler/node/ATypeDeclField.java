/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.node;

import org.linguate.arboratecompiler.analysis.*;

@SuppressWarnings("nls")
public final class ATypeDeclField extends PTypeDeclField
{
    private PTypeDeclFieldType _typeDeclFieldType_;
    private PTypeDeclFieldName _typeDeclFieldName_;

    public ATypeDeclField()
    {
        // Constructor
    }

    public ATypeDeclField(
        @SuppressWarnings("hiding") PTypeDeclFieldType _typeDeclFieldType_,
        @SuppressWarnings("hiding") PTypeDeclFieldName _typeDeclFieldName_)
    {
        // Constructor
        setTypeDeclFieldType(_typeDeclFieldType_);

        setTypeDeclFieldName(_typeDeclFieldName_);

    }

    @Override
    public Object clone()
    {
        return new ATypeDeclField(
            cloneNode(this._typeDeclFieldType_),
            cloneNode(this._typeDeclFieldName_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATypeDeclField(this);
    }

    public PTypeDeclFieldType getTypeDeclFieldType()
    {
        return this._typeDeclFieldType_;
    }

    public void setTypeDeclFieldType(PTypeDeclFieldType node)
    {
        if(this._typeDeclFieldType_ != null)
        {
            this._typeDeclFieldType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeDeclFieldType_ = node;
    }

    public PTypeDeclFieldName getTypeDeclFieldName()
    {
        return this._typeDeclFieldName_;
    }

    public void setTypeDeclFieldName(PTypeDeclFieldName node)
    {
        if(this._typeDeclFieldName_ != null)
        {
            this._typeDeclFieldName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeDeclFieldName_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._typeDeclFieldType_)
            + toString(this._typeDeclFieldName_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._typeDeclFieldType_ == child)
        {
            this._typeDeclFieldType_ = null;
            return;
        }

        if(this._typeDeclFieldName_ == child)
        {
            this._typeDeclFieldName_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._typeDeclFieldType_ == oldChild)
        {
            setTypeDeclFieldType((PTypeDeclFieldType) newChild);
            return;
        }

        if(this._typeDeclFieldName_ == oldChild)
        {
            setTypeDeclFieldName((PTypeDeclFieldName) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}