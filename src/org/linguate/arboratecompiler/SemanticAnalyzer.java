/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.ArrayList;
import java.util.List;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.InstructionCode;
import org.linguate.arboratecompiler.analysis.DepthFirstAdapter;
import org.linguate.arboratecompiler.node.*;

/**
 *
 * @author Phil Hutchinson
 */
public class SemanticAnalyzer extends DepthFirstAdapter {
    List<Instruction> instructions = new ArrayList<Instruction>();
    
    public void outAAddExpr(AAddExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_ADD);
    }

    public void outASubtractExpr(ASubtractExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_SUBTRACT);
    }

    public void outAMultiplyExpr(AMultiplyExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_MULTIPLY);
    }

    public void outADivideExpr(ADivideExpr node) {
        addInstructionIntToStack(processIntLit(node.getOp1()));
        addInstructionIntToStack(processIntLit(node.getOp2()));
        addInstructionByCode(InstructionCode.INTEGER_DIVIDE);
    }

    private static long processIntLit(PIntLit pIntLit) {
        AIntLit aIntLit = (AIntLit) pIntLit;
        return Long.parseLong(aIntLit.getIntString().getText());
    }
    
    private void addInstructionByCode(InstructionCode instructionCode) {
        instructions.add(new Instruction(instructionCode));
    }
    
    private void addInstructionIntToStack(long val) {
        instructions.add(new Instruction(InstructionCode.INTEGER_TO_STACK, val));
    }
    
    
}
