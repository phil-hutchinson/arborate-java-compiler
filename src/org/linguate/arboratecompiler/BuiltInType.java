/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arborate.vm.BaseType;

/**
 *
 * @author Phil Hutchinson
 */
public class BuiltInType {
    public static final ArborateType INTEGER = new ArborateType("int", BaseType.INTEGER);
    public static final ArborateType STRING = new ArborateType("string", BaseType.STRING);
    public static final ArborateType BOOLEAN = new ArborateType("boolean", BaseType.BOOLEAN);
    public static final ArborateType NODE = new ArborateType("node", BaseType.DICTIONARY);
}
