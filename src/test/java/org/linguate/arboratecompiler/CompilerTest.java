/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arboratecompiler;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
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
import org.linguate.arborate.vm.ArborateBoolean;
import org.linguate.arborate.vm.ArborateInteger;
import org.linguate.arborate.vm.ArborateMap;
import org.linguate.arborate.vm.ArborateString;
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

    private String getTestSource(String subpath) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        URL testUrl = classLoader.getResource("org/linguate/arboratecompiler/testsrc/" + subpath);
        File file  = new File(testUrl.getFile());
        
        return  new String(Files.readAllBytes(file.toPath()));
    }
    
    @Test
    public void testCompileSimpleAdd() throws Exception {
        String testSrc = getTestSource("mathoperations/testCompileSimpleAdd.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1125L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleSubtract() throws Exception {
        String testSrc = getTestSource("mathoperations/testCompileSimpleSubtract.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(-50L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleMultiply() throws Exception {
        String testSrc = getTestSource("mathoperations/testCompileSimpleMultiply.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5000L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleDivide() throws Exception {
        String testSrc = getTestSource("mathoperations/testCompileSimpleDivide.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }

    @Test
    public void testCompileSimpleTwoFunctions() throws Exception {
        String testSrc = getTestSource("functions/testCompileSimpleTwoFunctions.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(50L, result.getValue());
    }

    @Test
    public void testCompileSimpleFunctionCall() throws Exception {
        String testSrc = getTestSource("functions/testCompileSimpleFunctionCall.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(130L, result.getValue());
    }

    @Test
    public void testCompileDuplicateFunctionNameThrows() throws Exception {
        String testSrc = getTestSource("functions/testCompileDuplicateFunctionNameThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }
    
    @Test
    public void testCompileUnknownFunctionNameThrows() throws Exception {
        String testSrc = getTestSource("functions/testCompileUnknownFunctionNameThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }
    
    @Test
    public void testDummyVariable() throws Exception {
        String testSrc = getTestSource("variables/testDummyVariable.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(4L, result.getValue());
    }

    @Test
    public void testDummyVariableWithAssignment() throws Exception {
        String testSrc = getTestSource("variables/testDummyVariableWithAssignment.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(2L, result.getValue());
    }
    
    @Test
    public void testVariableAssignment() throws Exception {
        String testSrc = getTestSource("variables/testVariableAssignment.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(30L, result.getValue());
    }
    
    @Test
    public void testSameVariableMultipleFunctions() throws Exception {
        String testSrc = getTestSource("variables/testSameVariableMultipleFunctions.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(17L, result.getValue());
    }

    @Test
    public void testStatementsAfterReturnBypassed() throws Exception {
        String testSrc = getTestSource("returnvalues/testStatementsAfterReturnBypassed.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5L, result.getValue());
    }
    
    @Test
    public void testCompileDuplicateVariableNameInFunctionThrows() throws Exception {
        String testSrc = getTestSource("variables/testCompileDuplicateVariableNameInFunctionThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }
    
    @Test
    public void testCompileUnknownVariableNameAssignmentThrows() throws Exception {
        String testSrc = getTestSource("variables/testCompileUnknownVariableNameAssignmentThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }
    
    @Test
    public void testCompileUnknownVariableNameFetchThrows() throws Exception {
        String testSrc = getTestSource("variables/testCompileUnknownVariableNameFetchThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }
    
    @Test
    public void testCompileNoReturnThrows() throws Exception {
        String testSrc = getTestSource("returnvalues/testCompileNoReturnThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }

    @Test 
    public void testFunctionDummyParameter() throws Exception {
        String testSrc = getTestSource("functions/testCompileNoReturnThrows.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }

    @Test 
    public void testFunctionTwoDummyParameters() throws Exception {
        String testSrc = getTestSource("functions/testFunctionTwoDummyParameters.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(8L, result.getValue());
    }

    @Test 
    public void testFunctionCallDummyParameter() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallDummyParameter.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(20L, result.getValue());
    }

    @Test 
    public void testFunctionCallTwoDummyParameters() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallTwoDummyParameters.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(29L, result.getValue());
    }

    @Test 
    public void testFunctionCallDummyParameterMulti() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallDummyParameterMulti.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(104L, result.getValue());
    }
    
    @Test 
    public void testFunctionCallParameter() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallParameter.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(400L, result.getValue());
    }
    
    @Test 
    public void testFunctionCallMultiParameter() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallMultiParameter.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1936L, result.getValue());
    }

    @Test 
    public void testFunctionCallOrderSensitiveMultiParameter() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallOrderSensitiveMultiParameter.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(12L, result.getValue());
    }

    @Test 
    public void testTwoFunctionsSameParameterName() throws Exception {
        String testSrc = getTestSource("functions/testTwoFunctionsSameParameterName.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(2);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(25L, result.getValue());
    }

    @Test 
    public void testFunctionCallSameParameterNameThrows() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallSameParameterNameThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }

    @Test 
    public void testFunctionCallSameParameterNameAsVarNameThrows() throws Exception {
        String testSrc = getTestSource("functions/testFunctionCallSameParameterNameAsVarNameThrows.rb8");
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile(testSrc);
    }

    @Test 
    public void testZeroIntLiteral() throws Exception {
        String testSrc = getTestSource("expressions/testZeroIntLiteral.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(8L, result.getValue());
    }
    
    @Test
    public void testExpressionTermFactor() throws Exception {
        String testSrc = getTestSource("expressions/testExpressionTermFactor.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(108L, result.getValue());
    }

    @Test
    public void testFunctionCallWithExpressionTermFactor() throws Exception {
        String testSrc = getTestSource("expressions/testFunctionCallWithExpressionTermFactor.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(32L, result.getValue());
    }

    @Test
    public void testRepeatedOperationsWithExpressionTermFactor() throws Exception {
        String testSrc = getTestSource("expressions/testRepeatedOperationsWithExpressionTermFactor.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(44L, result.getValue());
    }

    @Test
    public void testNestedBrackets() throws Exception {
        String testSrc = getTestSource("expressions/testNestedBrackets.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(99L, result.getValue());
    }
    
    @Test
    public void testAddStrings() throws Exception {
        String testSrc = getTestSource("expressions/testAddStrings.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("something", result.getValue());
    }

    @Test
    public void testStringSubtractThrows() throws Exception {
        String testSrc = getTestSource("expressions/testStringSubtractThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }
    
    @Test
    public void testStringMultiplyThrows() throws Exception {
        String testSrc = getTestSource("expressions/testStringMultiplyThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testStringDivideThrows() throws Exception {
        String testSrc = getTestSource("expressions/testStringDivideThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testStringPlusIntegerThrows() throws Exception {
        String testSrc = getTestSource("expressions/testStringPlusIntegerThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testSimpleStringFunction() throws Exception {
        String testSrc = getTestSource("functions/testSimpleStringFunction.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("stringy", result.getValue());
    }

    @Test
    public void testNonMatchingReturnTypeThrows() throws Exception {
        String testSrc = getTestSource("returnvalues/testNonMatchingReturnTypeThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }
    
    @Test
    public void testFunctionCallWithStringReturn() throws Exception {
        String testSrc = getTestSource("returnvalues/testFunctionCallWithStringReturn.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("simpletest", result.getValue());
    }

    @Test
    public void testStringArguments() throws Exception {
        String testSrc = getTestSource("functions/testStringArguments.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("teststuff", result.getValue());
    }

    @Test
    public void testIncorrectArgCountThrows() throws Exception {
        String testSrc = getTestSource("functions/testIncorrectArgCountThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testIncorrectArgTypeThrows() throws Exception {
        String testSrc = getTestSource("functions/testIncorrectArgTypeThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testIncorrectArgTypeOrderThrows() throws Exception {
        String testSrc = getTestSource("functions/testIncorrectArgTypeOrderThrows.rb8");
        expectedException.expect(Exception.class);
        Compiler.compile(testSrc);
    }

    @Test
    public void testSimpleBooleanTrue() throws Exception {
        String testSrc = getTestSource("booleans/testSimpleBooleanTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testSimpleBooleanFalse() throws Exception {
        String testSrc = getTestSource("booleans/testSimpleBooleanFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }
    
    @Test
    public void testBooleanVar() throws Exception {
        String testSrc = getTestSource("booleans/testBooleanVar.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testBooleanParameter() throws Exception {
        String testSrc = getTestSource("booleans/testBooleanParameter.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntEqualsTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntEqualsFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testStringEqualsTrue () throws Exception {
        String testSrc = getTestSource("strings/testStringEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testStringEqualsFalse () throws Exception {
        String testSrc = getTestSource("strings/testStringEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testBooleanEqualsTrue () throws Exception {
        String testSrc = getTestSource("booleans/testBooleanEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testBooleanEqualsFalse () throws Exception {
        String testSrc = getTestSource("booleans/testBooleanEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntNotEqualsTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntNotEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntNotEqualsFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntNotEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testStringNotEqualsTrue () throws Exception {
        String testSrc = getTestSource("strings/testStringNotEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testStringNotEqualsFalse () throws Exception {
        String testSrc = getTestSource("strings/testStringNotEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testBooleanNotEqualsTrue () throws Exception {
        String testSrc = getTestSource("booleans/testBooleanNotEqualsTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testBooleanNotEqualsFalse () throws Exception {
        String testSrc = getTestSource("booleans/testBooleanNotEqualsFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerLessThanTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessThanTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntegerLessEqualTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessEqualTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntegerGreaterThanTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterThanTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntegerGreaterEqualTrue () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterEqualTrue.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntegerLessThanFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessThanFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerLessEqualFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessEqualFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerGreaterThanFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterThanFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerGreaterEqualFalse () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterEqualFalse.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerLessThanFalseWhenEqual () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessThanFalseWhenEqual.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerLessEqualTrueWhenEqual () throws Exception {
        String testSrc = getTestSource("integers/testIntegerLessEqualTrueWhenEqual.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testIntegerGreaterThanFalseWhenEqual () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterThanFalseWhenEqual.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testIntegerGreaterEqualTrueWhenEqual () throws Exception {
        String testSrc = getTestSource("integers/testIntegerGreaterEqualTrueWhenEqual.rb8");
        List<FunctionDefinition> functions = Compiler.compile(testSrc);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testEqualsNotMatchingTypesThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func boolean test() return 3 == true; endfunc");
    }

    @Test
    public void testNotEqualsNotMatchingTypesThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func boolean test() return false == \"false\"; endfunc");
    }

    @Test
    public void testComparisonNotMatchingTypesThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func boolean test() return false < 12; endfunc");
    }

    @Test
    public void testComparisonStringThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func boolean test() return \"a\" < \"b\"; endfunc");
    }

    @Test
    public void testComparisonBooleanThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func boolean test() return true >= false; endfunc");
    }
    
    @Test
    public void testNotTrue() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func boolean test() return !true; endfunc ");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }

    @Test
    public void testNotFalse() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func boolean test() return !true; endfunc ");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(false, result.getValue());
    }
    
    private void testTruthTable(String initialFunction, boolean ffResult, boolean ftResult, boolean tfResult, boolean ttResult) throws Exception {
        String program = initialFunction + 
                "func boolean ffTest() return test(false, false); endfunc\n" +
                "func boolean ftTest() return test(false, true); endfunc\n" +
                "func boolean tfTest() return test(true, false); endfunc\n" +
                "func boolean ttTest() return test(true, true); endfunc\n";
        List<FunctionDefinition> functions = Compiler.compile(program);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        boolean res1 = ((ArborateBoolean)virtualMachine.executeByNumber(1).get(0)).getValue();
        boolean res2 = ((ArborateBoolean)virtualMachine.executeByNumber(2).get(0)).getValue();
        boolean res3 = ((ArborateBoolean)virtualMachine.executeByNumber(3).get(0)).getValue();
        boolean res4 = ((ArborateBoolean)virtualMachine.executeByNumber(4).get(0)).getValue();
        
        assertEquals(ffResult, res1);
        assertEquals(ftResult, res2);
        assertEquals(tfResult, res3);
        assertEquals(ttResult, res4);
    }

    @Test
    public void testOr() throws Exception {
        String orFunction = "func boolean test(boolean a, boolean b) return a || b; endfunc\n";
        testTruthTable(orFunction, false, true, true, true);
    }

    @Test
    public void testAnd() throws Exception {
        String andFunction = "func boolean test(boolean a, boolean b) return a && b; endfunc\n";
        testTruthTable(andFunction, false, false, false, true);
    }

    @Test
    public void testXor() throws Exception {
        String xorFunction = "func boolean test(boolean a, boolean b) return a ^^ b; endfunc\n";
        testTruthTable(xorFunction, false, true, true, false);
    }

    @Test
    public void testCompoundLogical() throws Exception {
        String xorFunction = "func boolean test(boolean a, boolean b) return (a && a ^^ b) && (!a || a && b) ^^ !a; endfunc\n";
        // part 1: a && a ^^ b reduces to a ^^ b ---> (f t t f)
        // part 2: !a || a && b ---> (t t f f) || (f f f t) ----> (t t f t)
        // part 1 && part 2: (f t f f)
        // (part 1+2) ^^ !a ---> (f t f f) ^^ (t, t, f, f) ---> (t f f f)
        
        testTruthTable(xorFunction, true, false, false, false);
    }
    
    @Test
    public void testVariableUsedFromParentScope() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func boolean test() boolean a; block a = true; endblock return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateBoolean result = (ArborateBoolean) actualValue.get(0);
        assertEquals(true, result.getValue());
    }

    @Test
    public void testVariableUsedOutsideScopeThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func int test() int a; a = 3; block int b; b = 6; endblock return a + b; endfunc");
    }
    
    @Test
    public void testVariableReusedInScopes() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; block int b; b = 3; a = b + b; endblock block int b; b = 4; a = a + b; endblock return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10, result.getValue());
    }

    @Test
    public void testIfStatementExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 3; if (a > 2) a = a + 10; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(13, result.getValue());
    }

    @Test
    public void testIfStatementBypassed() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 2; if (a > 2) a = a + 10; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(2, result.getValue());
    }
    
    @Test
    public void testIfStatementReuseVariable() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 5; if (a > 3) int b; b = a; a = 10 + b; endif if (a > 3) int b; b = a; a = 20 + b; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(35, result.getValue());
    }

    @Test
    public void testVariableUsedIfScopeThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("func int test() int a; a = 3; if (a > 2)int b; b = 6; endif return a + b; endfunc");
    }
    
    @Test
    public void testSimpleIfElseWithIfExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 3; if (a > 2)  a = a + 10; else a = a * 100; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(13, result.getValue());
    }

    @Test
    public void testSimpleIfElseWithElseExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 3; if (a > 4) a = a + 10; else a = a * 100; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(300, result.getValue());
    }

    @Test
    public void testSimpleIfElseReusedVariable() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 100; if (a > 50) int b; b = a / 5; a = a + b; else int b; b = a / 2; a = a + b; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(120, result.getValue());
    }

    @Test
    public void testSimpleIfElseifWithIfExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 3; if (a > 2)  a = a * 10; elseif (a > 5) a = a + 100; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(30, result.getValue());
    }

    @Test
    public void testSimpleIfElseifWithElseifExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 3; if (a > 4)  a = a + 10; elseif (a < 4) a = a * 1000; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(3000, result.getValue());
    }

    @Test
    public void testSimpleIfElseifWithNeitherExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 4; if (a > 4)  a = a + 10; elseif (a < 4) a = a * 1000; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(4, result.getValue());
    }

    @Test
    public void testIfRepeatedElseif() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 23; if (a > 50)  a = a * 50;  elseif (a > 40) a = a * 40; elseif (a > 30) a = a * 30; elseif (a > 20) a = a * 20; elseif (a > 10) a = a * 10; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(460, result.getValue());
    }

    @Test
    public void testSimpleIfElseifReusedVariable() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 20; if (a > 50) int b; b = a / 5; a = a + b; elseif (a > 5) int b; b = a / 2; a = a + b; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(30, result.getValue());
    }

    @Test
    public void testIfElseifElseWithIfExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 200; if (a > 100) a = 1000 - a;elseif (a > 10)a = 100 - a; else  a = 10 - a; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(800, result.getValue());
    }

    @Test
    public void testIfElseifElseWithElseifExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 40; if (a > 100) a = 1000 - a; elseif (a > 10) a = 100 - a; else  a = 10 - a; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(60, result.getValue());
    }

    @Test
    public void testIfElseifElseWithElseExecuted() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 4; if (a > 100) a = 1000 - a; elseif (a > 10) a = 100 - a; else a = 10 - a; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(6, result.getValue());
    }

    @Test
    public void testIfRepeatedElseifElse() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test()  int a; a = 5; if (a > 50) a = a * 50; elseif (a > 40) a = a * 40; elseif (a > 30) a = a * 30; elseif (a > 20) a = a * 20; elseif (a > 10) a = a * 10; else a = 0; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(0, result.getValue());
    }

    @Test
    public void testIfElseifElseReusedVariable() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 9; if (a > 50) int b; b = a / 5; a = a + b; elseif (a > 30) int b; b = a / 3; a = a + b; else int b; b = a / 2; a = a + b; endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(13, result.getValue());
    }
    
    @Test
    public void testIfWithinIf() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 9; if (a < 50) a = a + 10; if (a < 50) a = a + 15; endif endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(34, result.getValue());
    
    }
    
    @Test
    public void testIfWithinElseIf() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 9; if (a < 5) a = a + 10; elseif (a < 50) a = a + 15; if (a > 5) a = a * 20; endif endif return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(480, result.getValue());
    
    }
    
    @Test
    public void testForwardReferenceFunction() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = square(5); return a; } func int square(int inVal) { return inVal * inVal; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(25, result.getValue());
    }
    
    @Test
    public void testRecursiveFunction() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() return power(3, 4); endfunc func int power(int base, int exponent) if (exponent < 0) return 0; elseif (exponent == 0) return 1; else return power(base, exponent - 1) * base; endif endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(81, result.getValue());
    }
    
    @Test
    public void testWhileStatement() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() return factorial(6); endfunc func int factorial(int inVal) int result; result = 1; int currVal; currVal = 1; while (currVal <= inVal) result = result * currVal; currVal = currVal + 1; endwhile return result; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(720, result.getValue());
    }
    
    @Test
    public void testWhileStatementZeroRepetitions() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int result; result = 10; while (result < 5) result = result + 1;endwhile return result; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10, result.getValue());
    }
    
    @Test
    public void testNestedWhileStatement() throws Exception {
        String program = 
                "func int test() \n" +
                "   return findPrime(9); \n" +
                "endfunc\n" +
                "func int findPrime(int primeToFind)\n" +
                "   int intToCheck;\n" +
                "   int primesFound;\n" +
                "   int lastPrimeFound;\n" +
                "   intToCheck = 2;\n" +
                "   primesFound = 0;\n" +
                "   while (primesFound < primeToFind)\n" +
                "       boolean factorFound;\n" +
                "       int factorToCheck;\n" +
                "       factorFound = false;\n" +
                "       factorToCheck = 2;\n" +
                "       while (factorToCheck * factorToCheck <= intToCheck && !factorFound)\n" +
                "           int quotient;\n" +
                "           quotient = intToCheck / factorToCheck;\n" +
                "           if (quotient * factorToCheck == intToCheck)\n" +
                "               factorFound = true;\n" +
                "           endif\n" +
                "           factorToCheck = factorToCheck + 1;\n" +
                "       endwhile\n" +
                "       if (!factorFound)\n" +
                "           primesFound = primesFound + 1;\n" +
                "           lastPrimeFound = intToCheck;\n" +
                "       endif\n" +
                "       intToCheck = intToCheck + 1;\n" +
                "   endwhile\n" +
                "   return lastPrimeFound;\n" +
                "endfunc\n";
        List<FunctionDefinition> functions = Compiler.compile(program);

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(23, result.getValue());
    }

    @Test
    public void testWhileConditionBooleanLiteral() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 0; while (false)  a = 1; endwhile return a; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(0, result.getValue());
    }

    @Test
    public void testWhileConditionNotBoolean() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("func int test() int a; a = 0; while (1)  a = 1; endwhile return a; endfunc");
    }

    @Test
    public void testSimpleNode() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func node test()  return new node; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateMap result = (ArborateMap) actualValue.get(0);
        ArborateString childrenIdentifier = new ArborateString(SemanticAnalyzer.NODE_CHILDREN_IDENTIFIER);
        assertEquals(true, result.has(childrenIdentifier));
    }

    @Test
    public void testSimpleNodeAssign() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("func node test() node abc; node def; abc = new node; def = abc; return def; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateMap result = (ArborateMap) actualValue.get(0);
        ArborateString childrenIdentifier = new ArborateString(SemanticAnalyzer.NODE_CHILDREN_IDENTIFIER);
        assertEquals(true, result.has(childrenIdentifier));
    }
    
    @Test
    public void testSimpleCustomType() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } func int test() return 21; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(21, result.getValue());
    }
    
    @Test
    public void testDuplicateTypeNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("type abc { int def; } type abc {int jkl; } func int test() return 21; endfunc");
    }

    @Test
    public void testDuplicateNameInTypeDeclarationThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("type abc { int def; string def; } func int test() return 21; endfunc");
    }

    @Test
    public void testCustomTypeContainingItselfThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("type abc { int def; abc abcvar; } func int test()  return 21; endfunc");
    }

    @Test
    public void testSameFieldNameInDifferentTypes() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } type xyz {string def; } func int test()  return 21; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(21, result.getValue());
    }
    
    @Test
    public void testDeclareVariableAsCustomType() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } func int test() abc abcVariable; return 21; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(21, result.getValue());
    }

    @Test
    public void testDeclareVariableAsCustomTypeWithNew() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } func int test()  abc abcVariable; abcVariable = new abc; return 21; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(21, result.getValue());
    }

    @Test
    public void testDeclareVariableAsCustomTypeWithNewInitialization() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } func int test() abc abcVariable; abcVariable = new abc { def: 3 + 13 }; return 21; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(21, result.getValue());
    }

    @Test
    public void testCustomVariableNewInitializationMultipleFields() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; string xyz; } func int test() abc abcVariable; abcVariable = new abc { def: 3 + 13, xyz: \"abcdefg\" }; return 331; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(331, result.getValue());
    }

    @Test
    public void testDeclareVariableAsCustomTypeWithInvalidFieldNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; } func int test() abc abcVariable; abcVariable = new abc { xyz: 3 + 13 }; return 21; endfunc");
    }

    @Test
    public void testDeclareVariableAsCustomTypeWithDuplicateFieldNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; int ghi; } func int test() abc abcVariable; abcVariable = new abc { def: 3 + 13, def: 5 * 5 }; return 55; endfunc");
    }

    @Test
    public void testCustomTypeContainingCustomType() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("type abc { int def; string xyz; } type foo { int bar; abc abcvar;} func int test() foo fooVar; fooVar = new foo { bar: 3, abcvar: new abc { def: 3 + 13, xyz: \"abcdefg\" }}; return 333; endfunc");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(333, result.getValue());
    }

}
