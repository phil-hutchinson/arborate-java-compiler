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

    @Test
    public void testCompileSimpleAdd() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int addTest() {return 125 + 1000;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1125L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleSubtract() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("  function int subtractTest(){   return   35 -   85  ;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(-50L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleMultiply() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int multiplyTest(){return 50*100;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5000L, result.getValue());
    }
    
    @Test
    public void testCompileSimpleDivide() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest() {return 87 / 8 ; }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }

    @Test
    public void testCompileSimpleTwoFunctions() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest() {return 500 / 10;  } function int dummy() {return 7 + 15;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(50L, result.getValue());
    }

    @Test
    public void testCompileSimpleFunctionCall() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest() {return 300 / 10;  } function int funcCallTest() {return divideTest() + 100;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(130L, result.getValue());
    }

    @Test
    public void testCompileDuplicateFunctionNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int duplicateName() {return 300 / 10;  } function int duplicateName() {return 6 + 100;}");
    }
    
    @Test
    public void testCompileUnknownFunctionNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int simpleTest() {return something() / 10;  }");
    }
    
    @Test
    public void testDummyVariable() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int dummyVariableTest() {int a; return 8 / 2;  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(4L, result.getValue());
    }

    @Test
    public void testDummyVariableWithAssignment() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int dummyAssignmentTest() {int a; a = 6 * 5; return 12 / 6;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(2L, result.getValue());
    }
    
    @Test
    public void testVariableAssignment() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int assignmentTest() {int a; a = 7 / 2; return 10 * a;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(30L, result.getValue());
    }
    
    @Test
    public void testSameVariableMultipleFunctions() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int subFunction() {int a; a = 2 + 2; return 2 * a;} function int mainFunction() {int a; a = 2 * subFunction(); return a + 1;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(17L, result.getValue());
    }

    @Test
    public void testStatementsAfterReturnBypassed() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int returnTest() {return 3 + 2; int a; a = 1000 + 100; return a * 50;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(5L, result.getValue());
    }
    
    @Test
    public void testCompileDuplicateVariableNameInFunctionThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int duplicateVariableName() {var a; var a; return 300 / 10;}");
    }
    
    @Test
    public void testCompileUnknownVariableNameAssignmentThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int unknownVariableAssignment() {a = 6 + 6; return 30 / 10;  }");
    }
    
    @Test
    public void testCompileUnknownVariableNameFetchThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int unknownVariableFetch() {return a / 10;  }");
    }
    
    @Test
    public void testCompileNoReturnThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int unknownVariableFetch() {int a; a = 3 / 10;}");
    }

    @Test 
    public void testFunctionDummyParameter() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest(int a) {return 6 / 3;  } function int funcCallTest() {return divideTest(7) * 5;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(10L, result.getValue());
    }

    @Test 
    public void testFunctionTwoDummyParameters() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest(int a, int b) {return 9 / 3;  } function int funcCallTest() {return divideTest(9, 3) + 5;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(8L, result.getValue());
    }

    @Test 
    public void testFunctionCallDummyParameter() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest(int zz) {return 8 / 2;  } function int funcCallTest() {return divideTest(14) * 5;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(20L, result.getValue());
    }

    @Test 
    public void testFunctionCallTwoDummyParameters() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest(int dummy1, int dummy2) {return 100 / 5;  } function int funcCallTest() {int a; a = 3 + 9; return divideTest(a, 22) + 9;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(29L, result.getValue());
    }

    @Test 
    public void testFunctionCallDummyParameterMulti() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int divideTest(int dummy) {return 10 / 2;  } function int funcCallTest() {return divideTest(divideTest(12)) + 99;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(104L, result.getValue());
    }
    
    @Test 
    public void testFunctionCallParameter() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int square(int val) {return val * val;  } function int funcCallTest() {return square(20) * 1;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(400L, result.getValue());
    }
    
    @Test 
    public void testFunctionCallMultiParameter() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int volume(int length, int width, int height) {int lw; lw = length * width; return lw * height;  } function int roomVolume() {return volume(14, 13, 8) + volume(6, 10, 8);}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(1936L, result.getValue());
    }

    @Test 
    public void testFunctionCallOrderSensitiveMultiParameter() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int subAndDivide(int a, int b, int c) {int diff; diff = a - b; return diff / c;  } function int test() {return subAndDivide(100, 40, 5) * 1;}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(12L, result.getValue());
    }

    @Test 
    public void testTwoFunctionsSameParameterName() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int times2(int val) {return val * 2;  } function int divideBy2(int val) {return val / 2;} function int test() {return times2(10) + divideBy2(10); }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(2);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(25L, result.getValue());
    }

    @Test 
    public void testFunctionCallSameParameterNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int areaOfSquare(int length, int length) {return length * length;  } function int test() {return areaOfSquare(5, 5) * 1;}");
    }

    @Test 
    public void testFunctionCallSameParameterNameAsVarNameThrows() throws Exception {
        expectedException.expect(Exception.class);
        List<FunctionDefinition> functions = Compiler.compile("function int subTest(int val) {int val; val = 1 * 1; return val * 1;  } function int test() {return subTest(1) * 1;}");
    }

    @Test 
    public void testZeroIntLiteral() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int zeroLiteral() {return 8 + 0;  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(8L, result.getValue());
    }
    
    @Test
    public void testExpressionTermFactor() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int etfTest() {int a; int b; int c; a = 3; b = 6 / 3 * 2; c = 7 + 5 * 5; return a * (b + c);  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(108L, result.getValue());
    }

    @Test
    public void testFunctionCallWithExpressionTermFactor() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int double(int a) {return a * 2;  } function int test() {int a; int b; a = double(2 + 5); b = double((1 + 3) / (2 * 2)); b = b + a; return double(b);}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(32L, result.getValue());
    }

    @Test
    public void testRepeatedOperationsWithExpressionTermFactor() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int etfRepeatedOperationsTest() {int a; int b; int c; a = 3 + 2 + 5 + 1; b = 20 - 5 - 5 - 5 + 3; c = a * b * 2 * 6 * 3 * 5; return c / 9 / 10 / 4;  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(44L, result.getValue());
    }

    @Test
    public void testNestedBrackets() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function int etfRepeatedOperationsTest() {int a; int b; a = 3 - (12 - (25 - (9 - 4))); b = 36 / (32 / (40 / (15 / 3))); return a * b;  }");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateInteger result = (ArborateInteger) actualValue.get(0);
        assertEquals(99L, result.getValue());
    }
    
    @Test
    public void testAddStrings() throws Exception {
        // can't return a string yet, so just test that it compiles/executes
        List<FunctionDefinition> functions = Compiler.compile("function string stringAdd() {string a; string b; string c; a = \"some\"; b = \"thing\"; c = a + b; return c;} ");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("something", result.getValue());
    }

    @Test
    public void testStringSubtractThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int stringSubtract() {string a; string b; string c; c = a - b; return 14;} ");
    }
    
    @Test
    public void testStringMultiplyThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int stringSubtract() {string a; string b; string c; c = a * b; return 14;} ");
    }

    @Test
    public void testStringDivideThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int stringSubtract() {string a; string b; string c; c = a / b; return 14;} ");
    }

    @Test
    public void testStringPlusIntegerThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int stringSubtract() {string a; int b; string c; c = a + b; return 14;} ");
    }

    @Test
    public void testSimpleStringFunction() throws Exception {
        // can't return a string yet, so just test that it compiles/executes
        List<FunctionDefinition> functions = Compiler.compile("function string simpleString() { return \"stringy\"; } ");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.execute();
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("stringy", result.getValue());
    }

    @Test
    public void testNonMatchingReturnTypeThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int stringSubtract() {return \"abc\";} ");
    }
    
    @Test
    public void testFunctionCallWithStringReturn() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function string test() {return \"test\";  } function string testCall() {return \"simple\" + test();}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("simpletest", result.getValue());
    }

    @Test
    public void testStringArguments() throws Exception {
        List<FunctionDefinition> functions = Compiler.compile("function string test(string abc) {return \"test\" + abc;  } function string testCall() {return test(\"stuff\");}");

        VirtualMachine virtualMachine = new VirtualMachine(functions);

        List<Object> actualValue = virtualMachine.executeByNumber(1);
        assertEquals(1, actualValue.size());
        ArborateString result = (ArborateString) actualValue.get(0);
        assertEquals("teststuff", result.getValue());
    }

    @Test
    public void testIncorrectArgCountThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int addSimple(int a, int b) {return a + b;} function int test() { return addSimple(2); }");
    }

    @Test
    public void testIncorrectArgTypeThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int addSimple(int a) {return a + 5;} function int test() { string a; a = \"something\"; return addSimple(a); }");
    }

    @Test
    public void testIncorrectArgTypeOrderThrows() throws Exception {
        expectedException.expect(Exception.class);
        Compiler.compile("function int addSimple(int a, string b) {return a + 2;} function int test() { string str; int num; str = \"something\"; num = 3; return addSimple(str, num); }");
    }

}
