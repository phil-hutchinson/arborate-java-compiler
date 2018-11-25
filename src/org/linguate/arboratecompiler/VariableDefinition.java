/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

/**
 *
 * @author Phil Hutchinson
 */
public class VariableDefinition {
    long variablePosition;
    BasicType basicType;

    public VariableDefinition(long variablePosition, BasicType basicType) {
        this.variablePosition = variablePosition;
        this.basicType = basicType;
    }
}
