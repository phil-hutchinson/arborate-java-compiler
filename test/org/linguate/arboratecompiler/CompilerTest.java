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
import org.junit.Rule;
import org.junit.rules.ExpectedException;
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
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
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
        List<FunctionDefinition> functions = Compiler.compile("function addTest() {125 + 1000}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1125L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleSubtract() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("  function subtractTest(){  35 -   85}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(-50L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleMultiply() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function multiplyTest(){50*100}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5000L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleDivide() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function divideTest() {87 / 8  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }

    @Test
    public void testCompileSimpleTwoFunctions() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function divideTest() {500 / 10  } function dummy() {7 + 15}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(50L, result.getValue());
    }

    @Test
    public void testCompileSimpleFunctionCall() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function divideTest() {300 / 10  } function funcCallTest() {divideTest() + 100}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(130L, result.getValue());
    }

    @Test
    public void testCompileDuplicateFunctionNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function duplicateName() {300 / 10  } function duplicateName() {6 + 100}");
    }
    
    @Test
    public void testCompileUnknownFunctionNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function simpleTest() {something() / 10  }");
    }
}
