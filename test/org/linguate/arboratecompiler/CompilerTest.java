/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.linguate.arborate.vm.ArborateInteger;
import org.linguate.arborate.vm.BaseType;
import org.linguate.arborate.vm.FunctionDefinition;
import org.linguate.arborate.vm.Instruction;
import org.linguate.arborate.vm.VirtualMachine;
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
        List<Instruction> instructions = Compiler.compile("125 + 1000");

        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1125L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleSubtract() throws Exception {
        List<Instruction> instructions = Compiler.compile("35 -   85");

        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(-50L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleMultiply() throws Exception {
        List<Instruction> instructions = Compiler.compile("50*100");

        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5000L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleDivide() throws Exception {
        List<Instruction> instructions = Compiler.compile("87 / 8");

        FunctionDefinition mainFunc = new FunctionDefinition(instructions, 0, Arrays.asList(), Arrays.asList(BaseType.INTEGER));
        
        VirtualMachine virtualMachine = new VirtualMachine(Arrays.asList(mainFunc));

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }
}
