/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.HashMap;
import java.util.Map;
import org.linguate.arborate.vm.BaseType;

/**
 *
 * @author Phil Hutchinson
 */
public class ArborateStructType extends ArborateType {
    private Map<String, ArborateType> fieldMap = new HashMap<>();
    
    public ArborateStructType() {
    }
    
    public void AddField(ArborateType arborateType, String fieldName) {
        if (fieldMap.containsKey(fieldName)) {
            throw new RuntimeException("Duplicate field '" + fieldName + "' in custom type '" + name + "'");
        }
        
        fieldMap.put(fieldName, arborateType);
    }
    
    public ArborateType GetFieldByName(String fieldName) {
        if (!fieldMap.containsKey(fieldName)) {
            throw new RuntimeException("Could not locate field '" + fieldName + "' in custom type '" + name + "'");
        }
        return fieldMap.get(fieldName);
    }
}
