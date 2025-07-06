/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Phil Hutchinson
 */
public class VariableInitializationContext {
    private final ArborateType arborateType;
    private final Set<String> fieldsAdded;
    
    public VariableInitializationContext(ArborateType arborateType) {
        this.fieldsAdded = new HashSet<>();
        this.arborateType = arborateType;
    }

    public ArborateType getArborateType() {
        return arborateType;
    }
    
    public void SetFieldAdded(String fieldName) {
        if (fieldsAdded.contains(fieldName)) {
            throw new RuntimeException("Field already added.");
        }
        fieldsAdded.add(fieldName);
    }
    
    public boolean IsFieldAdded(String fieldName) {
        return fieldsAdded.contains(fieldName);
    }
}
