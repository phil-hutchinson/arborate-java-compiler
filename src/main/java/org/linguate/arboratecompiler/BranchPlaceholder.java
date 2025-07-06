/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import org.linguate.arborate.vm.InstructionCode;

/**
 *
 * @author Phil Hutchinson
 */
public class BranchPlaceholder {
    public InstructionCode instructionCode;
    public BranchTarget branchTarget;

    public BranchPlaceholder(InstructionCode instructionCode, BranchTarget branchTarget) {
        this.instructionCode = instructionCode;
        this.branchTarget = branchTarget;
    }
    
}
