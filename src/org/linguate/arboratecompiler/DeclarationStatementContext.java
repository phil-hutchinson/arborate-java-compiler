/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arboratecompiler.node.TIdentifier;

/**
 *
 * @author Phil Hutchinson
 */
public class DeclarationStatementContext extends StatementContext {
    TIdentifier varIdentifier = null;
    ArborateType arborateType = null;
}
