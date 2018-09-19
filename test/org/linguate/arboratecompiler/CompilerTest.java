/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.linguate.arboratecompiler.node.*;

/**
 *
 * @author Phil Hutchinson
 */
public class CompilerTest {
    
    public CompilerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCompileSimpleAdd() throws Exception {
        Start start = Compiler.compile("125 + 1000");
        
        AGrammar grammar =(AGrammar) start.getPGrammar();
        
        AIntLit op1 = (AIntLit) grammar.getOp1();
        assertEquals("125", op1.getIntString().getText());
        
        POperator operand = grammar.getOperand();
        assertThat(operand, instanceOf(AAddOperator.class));
        
        AIntLit op2 = (AIntLit) grammar.getOp2();
        assertEquals("1000", op2.getIntString().getText());
    }
    
}
