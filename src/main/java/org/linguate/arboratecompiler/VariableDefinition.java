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
    ArborateType arborateType;
    String name;

    public VariableDefinition(long variablePosition, ArborateType arborateType, String name) {
        this.variablePosition = variablePosition;
        this.arborateType = arborateType;
        this.name = name;
    }
}
