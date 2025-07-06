/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.ArrayList;
import java.util.List;
import org.linguate.arboratecompiler.node.TIdentifier;

/**
 *
 * @author Phil Hutchinson
 */
public class FunctionCallContext {
    TIdentifier callIdentifier = null;
    List<ArborateType> paramTypes = new ArrayList<>();
}
