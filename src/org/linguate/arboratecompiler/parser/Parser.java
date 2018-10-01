/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.linguate.arboratecompiler.parser;

import org.linguate.arboratecompiler.lexer.*;
import org.linguate.arboratecompiler.node.*;
import org.linguate.arboratecompiler.analysis.*;
import java.util.*;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

@SuppressWarnings("nls")
public class Parser
{
    public final Analysis ignoredTokens = new AnalysisAdapter();

    protected ArrayList<Object> nodeList;

    private final Lexer lexer;
    private final ListIterator<Object> stack = new LinkedList<Object>().listIterator();
    private int last_pos;
    private int last_line;
    private Token last_token;
    private final TokenIndex converter = new TokenIndex();
    private final int[] action = new int[2];

    private final static int SHIFT = 0;
    private final static int REDUCE = 1;
    private final static int ACCEPT = 2;
    private final static int ERROR = 3;

    public Parser(@SuppressWarnings("hiding") Lexer lexer)
    {
        this.lexer = lexer;
    }

    @SuppressWarnings({"unchecked","unused"})
    private void push(int numstate, ArrayList<Object> listNode) throws ParserException, LexerException, IOException
    {
        this.nodeList = listNode;

        if(!this.stack.hasNext())
        {
            this.stack.add(new State(numstate, this.nodeList));
            return;
        }

        State s = (State) this.stack.next();
        s.state = numstate;
        s.nodes = this.nodeList;
    }

    private int goTo(int index)
    {
        int state = state();
        int low = 1;
        int high = gotoTable[index].length - 1;
        int value = gotoTable[index][0][1];

        while(low <= high)
        {
            // int middle = (low + high) / 2;
            int middle = (low + high) >>> 1;

            if(state < gotoTable[index][middle][0])
            {
                high = middle - 1;
            }
            else if(state > gotoTable[index][middle][0])
            {
                low = middle + 1;
            }
            else
            {
                value = gotoTable[index][middle][1];
                break;
            }
        }

        return value;
    }

    private int state()
    {
        State s = (State) this.stack.previous();
        this.stack.next();
        return s.state;
    }

    private ArrayList<Object> pop()
    {
        return ((State) this.stack.previous()).nodes;
    }

    private int index(Switchable token)
    {
        this.converter.index = -1;
        token.apply(this.converter);
        return this.converter.index;
    }

    @SuppressWarnings("unchecked")
    public Start parse() throws ParserException, LexerException, IOException
    {
        push(0, null);
        List<Node> ign = null;
        while(true)
        {
            while(index(this.lexer.peek()) == -1)
            {
                if(ign == null)
                {
                    ign = new LinkedList<Node>();
                }

                ign.add(this.lexer.next());
            }

            if(ign != null)
            {
                this.ignoredTokens.setIn(this.lexer.peek(), ign);
                ign = null;
            }

            this.last_pos = this.lexer.peek().getPos();
            this.last_line = this.lexer.peek().getLine();
            this.last_token = this.lexer.peek();

            int index = index(this.lexer.peek());
            this.action[0] = Parser.actionTable[state()][0][1];
            this.action[1] = Parser.actionTable[state()][0][2];

            int low = 1;
            int high = Parser.actionTable[state()].length - 1;

            while(low <= high)
            {
                int middle = (low + high) / 2;

                if(index < Parser.actionTable[state()][middle][0])
                {
                    high = middle - 1;
                }
                else if(index > Parser.actionTable[state()][middle][0])
                {
                    low = middle + 1;
                }
                else
                {
                    this.action[0] = Parser.actionTable[state()][middle][1];
                    this.action[1] = Parser.actionTable[state()][middle][2];
                    break;
                }
            }

            switch(this.action[0])
            {
                case SHIFT:
		    {
		        ArrayList<Object> list = new ArrayList<Object>();
		        list.add(this.lexer.next());
                        push(this.action[1], list);
                    }
		    break;
                case REDUCE:
                    {
                        int reduction = this.action[1];
                        if(reduction < 500) reduce_0(reduction);
                    }
                    break;
                case ACCEPT:
                    {
                        EOF node2 = (EOF) this.lexer.next();
                        PProgram node1 = (PProgram) pop().get(0);
                        Start node = new Start(node1, node2);
                        return node;
                    }
                case ERROR:
                    throw new ParserException(this.last_token,
                        "[" + this.last_line + "," + this.last_pos + "] " +
                        Parser.errorMessages[Parser.errors[this.action[1]]]);
            }
        }
    }

    private void reduce_0(int reduction) throws IOException, LexerException, ParserException
    {
        switch(reduction)
        {
            case 0: /* reduce AProgram */
            {
                ArrayList<Object> list = new0();
                push(goTo(0), list);
            }
            break;
            case 1: /* reduce ASingleFunctionList */
            {
                ArrayList<Object> list = new1();
                push(goTo(1), list);
            }
            break;
            case 2: /* reduce AMultipleFunctionList */
            {
                ArrayList<Object> list = new2();
                push(goTo(1), list);
            }
            break;
            case 3: /* reduce AFunc */
            {
                ArrayList<Object> list = new3();
                push(goTo(2), list);
            }
            break;
            case 4: /* reduce ASingleFuncBody */
            {
                ArrayList<Object> list = new4();
                push(goTo(3), list);
            }
            break;
            case 5: /* reduce AMultipleFuncBody */
            {
                ArrayList<Object> list = new5();
                push(goTo(3), list);
            }
            break;
            case 6: /* reduce ADeclarationStatement */
            {
                ArrayList<Object> list = new6();
                push(goTo(4), list);
            }
            break;
            case 7: /* reduce AAssignmentStatement */
            {
                ArrayList<Object> list = new7();
                push(goTo(4), list);
            }
            break;
            case 8: /* reduce AReturnStatement */
            {
                ArrayList<Object> list = new8();
                push(goTo(4), list);
            }
            break;
            case 9: /* reduce AAddExpr */
            {
                ArrayList<Object> list = new9();
                push(goTo(5), list);
            }
            break;
            case 10: /* reduce ASubtractExpr */
            {
                ArrayList<Object> list = new10();
                push(goTo(5), list);
            }
            break;
            case 11: /* reduce AMultiplyExpr */
            {
                ArrayList<Object> list = new11();
                push(goTo(5), list);
            }
            break;
            case 12: /* reduce ADivideExpr */
            {
                ArrayList<Object> list = new12();
                push(goTo(5), list);
            }
            break;
            case 13: /* reduce AIntLitValue */
            {
                ArrayList<Object> list = new13();
                push(goTo(6), list);
            }
            break;
            case 14: /* reduce AFuncCallValue */
            {
                ArrayList<Object> list = new14();
                push(goTo(6), list);
            }
            break;
            case 15: /* reduce AVarFetchValue */
            {
                ArrayList<Object> list = new15();
                push(goTo(6), list);
            }
            break;
            case 16: /* reduce AFuncName */
            {
                ArrayList<Object> list = new16();
                push(goTo(7), list);
            }
            break;
            case 17: /* reduce AFuncCallName */
            {
                ArrayList<Object> list = new17();
                push(goTo(8), list);
            }
            break;
            case 18: /* reduce AVarDeclType */
            {
                ArrayList<Object> list = new18();
                push(goTo(9), list);
            }
            break;
            case 19: /* reduce AVarDeclName */
            {
                ArrayList<Object> list = new19();
                push(goTo(10), list);
            }
            break;
            case 20: /* reduce AVarAssignName */
            {
                ArrayList<Object> list = new20();
                push(goTo(11), list);
            }
            break;
            case 21: /* reduce AVarFetchName */
            {
                ArrayList<Object> list = new21();
                push(goTo(12), list);
            }
            break;
        }
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new0() /* reduce AProgram */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PProgram pprogramNode1;
        {
            // Block
        LinkedList<Object> listNode3 = new LinkedList<Object>();
        {
            // Block
        LinkedList<Object> listNode2 = new LinkedList<Object>();
        listNode2 = (LinkedList)nodeArrayList1.get(0);
	if(listNode2 != null)
	{
	  listNode3.addAll(listNode2);
	}
        }

        pprogramNode1 = new AProgram(listNode3);
        }
	nodeList.add(pprogramNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new1() /* reduce ASingleFunctionList */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        LinkedList<Object> listNode2 = new LinkedList<Object>();
        {
            // Block
        PFunc pfuncNode1;
        pfuncNode1 = (PFunc)nodeArrayList1.get(0);
	if(pfuncNode1 != null)
	{
	  listNode2.add(pfuncNode1);
	}
        }
	nodeList.add(listNode2);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new2() /* reduce AMultipleFunctionList */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        LinkedList<Object> listNode3 = new LinkedList<Object>();
        {
            // Block
        PFunc pfuncNode1;
        LinkedList<Object> listNode2 = new LinkedList<Object>();
        pfuncNode1 = (PFunc)nodeArrayList1.get(0);
        listNode2 = (LinkedList)nodeArrayList2.get(0);
	if(pfuncNode1 != null)
	{
	  listNode3.add(pfuncNode1);
	}
	if(listNode2 != null)
	{
	  listNode3.addAll(listNode2);
	}
        }
	nodeList.add(listNode3);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new3() /* reduce AFunc */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList7 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList6 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList5 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList4 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PFunc pfuncNode1;
        {
            // Block
        PFuncName pfuncnameNode2;
        LinkedList<Object> listNode4 = new LinkedList<Object>();
        pfuncnameNode2 = (PFuncName)nodeArrayList2.get(0);
        {
            // Block
        LinkedList<Object> listNode3 = new LinkedList<Object>();
        listNode3 = (LinkedList)nodeArrayList6.get(0);
	if(listNode3 != null)
	{
	  listNode4.addAll(listNode3);
	}
        }

        pfuncNode1 = new AFunc(pfuncnameNode2, listNode4);
        }
	nodeList.add(pfuncNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new4() /* reduce ASingleFuncBody */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        LinkedList<Object> listNode2 = new LinkedList<Object>();
        {
            // Block
        PStatement pstatementNode1;
        pstatementNode1 = (PStatement)nodeArrayList1.get(0);
	if(pstatementNode1 != null)
	{
	  listNode2.add(pstatementNode1);
	}
        }
	nodeList.add(listNode2);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new5() /* reduce AMultipleFuncBody */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        LinkedList<Object> listNode3 = new LinkedList<Object>();
        {
            // Block
        PStatement pstatementNode1;
        LinkedList<Object> listNode2 = new LinkedList<Object>();
        pstatementNode1 = (PStatement)nodeArrayList1.get(0);
        listNode2 = (LinkedList)nodeArrayList3.get(0);
	if(pstatementNode1 != null)
	{
	  listNode3.add(pstatementNode1);
	}
	if(listNode2 != null)
	{
	  listNode3.addAll(listNode2);
	}
        }
	nodeList.add(listNode3);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new6() /* reduce ADeclarationStatement */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PStatement pstatementNode1;
        {
            // Block
        PVarDeclType pvardecltypeNode2;
        PVarDeclName pvardeclnameNode3;
        pvardecltypeNode2 = (PVarDeclType)nodeArrayList1.get(0);
        pvardeclnameNode3 = (PVarDeclName)nodeArrayList2.get(0);

        pstatementNode1 = new ADeclarationStatement(pvardecltypeNode2, pvardeclnameNode3);
        }
	nodeList.add(pstatementNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new7() /* reduce AAssignmentStatement */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PStatement pstatementNode1;
        {
            // Block
        PVarAssignName pvarassignnameNode2;
        PExpr pexprNode3;
        pvarassignnameNode2 = (PVarAssignName)nodeArrayList1.get(0);
        pexprNode3 = (PExpr)nodeArrayList3.get(0);

        pstatementNode1 = new AAssignmentStatement(pvarassignnameNode2, pexprNode3);
        }
	nodeList.add(pstatementNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new8() /* reduce AReturnStatement */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PStatement pstatementNode1;
        {
            // Block
        PExpr pexprNode2;
        pexprNode2 = (PExpr)nodeArrayList2.get(0);

        pstatementNode1 = new AReturnStatement(pexprNode2);
        }
	nodeList.add(pstatementNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new9() /* reduce AAddExpr */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PExpr pexprNode1;
        {
            // Block
        PValue pvalueNode2;
        PValue pvalueNode3;
        pvalueNode2 = (PValue)nodeArrayList1.get(0);
        pvalueNode3 = (PValue)nodeArrayList3.get(0);

        pexprNode1 = new AAddExpr(pvalueNode2, pvalueNode3);
        }
	nodeList.add(pexprNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new10() /* reduce ASubtractExpr */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PExpr pexprNode1;
        {
            // Block
        PValue pvalueNode2;
        PValue pvalueNode3;
        pvalueNode2 = (PValue)nodeArrayList1.get(0);
        pvalueNode3 = (PValue)nodeArrayList3.get(0);

        pexprNode1 = new ASubtractExpr(pvalueNode2, pvalueNode3);
        }
	nodeList.add(pexprNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new11() /* reduce AMultiplyExpr */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PExpr pexprNode1;
        {
            // Block
        PValue pvalueNode2;
        PValue pvalueNode3;
        pvalueNode2 = (PValue)nodeArrayList1.get(0);
        pvalueNode3 = (PValue)nodeArrayList3.get(0);

        pexprNode1 = new AMultiplyExpr(pvalueNode2, pvalueNode3);
        }
	nodeList.add(pexprNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new12() /* reduce ADivideExpr */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PExpr pexprNode1;
        {
            // Block
        PValue pvalueNode2;
        PValue pvalueNode3;
        pvalueNode2 = (PValue)nodeArrayList1.get(0);
        pvalueNode3 = (PValue)nodeArrayList3.get(0);

        pexprNode1 = new ADivideExpr(pvalueNode2, pvalueNode3);
        }
	nodeList.add(pexprNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new13() /* reduce AIntLitValue */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PValue pvalueNode1;
        {
            // Block
        TIntString tintstringNode2;
        tintstringNode2 = (TIntString)nodeArrayList1.get(0);

        pvalueNode1 = new AIntLitValue(tintstringNode2);
        }
	nodeList.add(pvalueNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new14() /* reduce AFuncCallValue */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PValue pvalueNode1;
        {
            // Block
        PFuncCallName pfunccallnameNode2;
        pfunccallnameNode2 = (PFuncCallName)nodeArrayList1.get(0);

        pvalueNode1 = new AFuncCallValue(pfunccallnameNode2);
        }
	nodeList.add(pvalueNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new15() /* reduce AVarFetchValue */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PValue pvalueNode1;
        {
            // Block
        PVarFetchName pvarfetchnameNode2;
        pvarfetchnameNode2 = (PVarFetchName)nodeArrayList1.get(0);

        pvalueNode1 = new AVarFetchValue(pvarfetchnameNode2);
        }
	nodeList.add(pvalueNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new16() /* reduce AFuncName */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PFuncName pfuncnameNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pfuncnameNode1 = new AFuncName(tidentifierNode2);
        }
	nodeList.add(pfuncnameNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new17() /* reduce AFuncCallName */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PFuncCallName pfunccallnameNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pfunccallnameNode1 = new AFuncCallName(tidentifierNode2);
        }
	nodeList.add(pfunccallnameNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new18() /* reduce AVarDeclType */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PVarDeclType pvardecltypeNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pvardecltypeNode1 = new AVarDeclType(tidentifierNode2);
        }
	nodeList.add(pvardecltypeNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new19() /* reduce AVarDeclName */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PVarDeclName pvardeclnameNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pvardeclnameNode1 = new AVarDeclName(tidentifierNode2);
        }
	nodeList.add(pvardeclnameNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new20() /* reduce AVarAssignName */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PVarAssignName pvarassignnameNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pvarassignnameNode1 = new AVarAssignName(tidentifierNode2);
        }
	nodeList.add(pvarassignnameNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new21() /* reduce AVarFetchName */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PVarFetchName pvarfetchnameNode1;
        {
            // Block
        TIdentifier tidentifierNode2;
        tidentifierNode2 = (TIdentifier)nodeArrayList1.get(0);

        pvarfetchnameNode1 = new AVarFetchName(tidentifierNode2);
        }
	nodeList.add(pvarfetchnameNode1);
        return nodeList;
    }



    private static int[][][] actionTable;
/*      {
			{{-1, ERROR, 0}, {0, SHIFT, 1}, },
			{{-1, ERROR, 1}, {13, SHIFT, 5}, },
			{{-1, ERROR, 2}, {14, ACCEPT, -1}, },
			{{-1, REDUCE, 0}, },
			{{-1, REDUCE, 1}, {0, SHIFT, 1}, },
			{{-1, REDUCE, 16}, },
			{{-1, ERROR, 6}, {4, SHIFT, 8}, },
			{{-1, REDUCE, 2}, },
			{{-1, ERROR, 8}, {5, SHIFT, 9}, },
			{{-1, ERROR, 9}, {2, SHIFT, 10}, },
			{{-1, ERROR, 10}, {1, SHIFT, 11}, {13, SHIFT, 12}, },
			{{-1, ERROR, 11}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, REDUCE, 20}, {13, REDUCE, 18}, },
			{{-1, ERROR, 13}, {3, SHIFT, 23}, },
			{{-1, ERROR, 14}, {11, SHIFT, 24}, },
			{{-1, ERROR, 15}, {13, SHIFT, 25}, },
			{{-1, ERROR, 16}, {10, SHIFT, 27}, },
			{{-1, REDUCE, 13}, },
			{{-1, REDUCE, 21}, {4, REDUCE, 17}, },
			{{-1, REDUCE, 8}, },
			{{-1, ERROR, 20}, {6, SHIFT, 28}, {7, SHIFT, 29}, {8, SHIFT, 30}, {9, SHIFT, 31}, },
			{{-1, ERROR, 21}, {4, SHIFT, 32}, },
			{{-1, REDUCE, 15}, },
			{{-1, REDUCE, 3}, },
			{{-1, REDUCE, 4}, {1, SHIFT, 11}, {13, SHIFT, 12}, },
			{{-1, REDUCE, 19}, },
			{{-1, REDUCE, 6}, },
			{{-1, ERROR, 27}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, ERROR, 28}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, ERROR, 29}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, ERROR, 30}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, ERROR, 31}, {12, SHIFT, 17}, {13, SHIFT, 18}, },
			{{-1, ERROR, 32}, {5, SHIFT, 39}, },
			{{-1, REDUCE, 5}, },
			{{-1, REDUCE, 7}, },
			{{-1, REDUCE, 9}, },
			{{-1, REDUCE, 10}, },
			{{-1, REDUCE, 11}, },
			{{-1, REDUCE, 12}, },
			{{-1, REDUCE, 14}, },
        };*/
    private static int[][][] gotoTable;
/*      {
			{{-1, 2}, },
			{{-1, 3}, {4, 7}, },
			{{-1, 4}, },
			{{-1, 13}, {24, 33}, },
			{{-1, 14}, },
			{{-1, 19}, {27, 34}, },
			{{-1, 20}, {28, 35}, {29, 36}, {30, 37}, {31, 38}, },
			{{-1, 6}, },
			{{-1, 21}, },
			{{-1, 15}, },
			{{-1, 26}, },
			{{-1, 16}, },
			{{-1, 22}, },
        };*/
    private static String[] errorMessages;
/*      {
			"expecting: 'function'",
			"expecting: identifier",
			"expecting: EOF",
			"expecting: 'function', EOF",
			"expecting: '('",
			"expecting: ')'",
			"expecting: '{'",
			"expecting: 'return', identifier",
			"expecting: int string, identifier",
			"expecting: '=', identifier",
			"expecting: '}'",
			"expecting: ';'",
			"expecting: '='",
			"expecting: '+', '-', '*', '/', ';'",
			"expecting: '(', '+', '-', '*', '/', ';'",
			"expecting: '+', '-', '*', '/'",
			"expecting: 'return', '}', identifier",
        };*/
    private static int[] errors;
/*      {
			0, 1, 2, 2, 3, 4, 4, 2, 5, 6, 7, 8, 9, 10, 11, 1, 12, 13, 14, 11, 15, 4, 13, 3, 16, 11, 11, 8, 8, 8, 8, 8, 5, 10, 11, 11, 11, 11, 11, 13, 
        };*/

    static 
    {
        try
        {
            DataInputStream s = new DataInputStream(
                new BufferedInputStream(
                Parser.class.getResourceAsStream("parser.dat")));

            // read actionTable
            int length = s.readInt();
            Parser.actionTable = new int[length][][];
            for(int i = 0; i < Parser.actionTable.length; i++)
            {
                length = s.readInt();
                Parser.actionTable[i] = new int[length][3];
                for(int j = 0; j < Parser.actionTable[i].length; j++)
                {
                for(int k = 0; k < 3; k++)
                {
                    Parser.actionTable[i][j][k] = s.readInt();
                }
                }
            }

            // read gotoTable
            length = s.readInt();
            gotoTable = new int[length][][];
            for(int i = 0; i < gotoTable.length; i++)
            {
                length = s.readInt();
                gotoTable[i] = new int[length][2];
                for(int j = 0; j < gotoTable[i].length; j++)
                {
                for(int k = 0; k < 2; k++)
                {
                    gotoTable[i][j][k] = s.readInt();
                }
                }
            }

            // read errorMessages
            length = s.readInt();
            errorMessages = new String[length];
            for(int i = 0; i < errorMessages.length; i++)
            {
                length = s.readInt();
                StringBuffer buffer = new StringBuffer();

                for(int j = 0; j < length; j++)
                {
                buffer.append(s.readChar());
                }
                errorMessages[i] = buffer.toString();
            }

            // read errors
            length = s.readInt();
            errors = new int[length];
            for(int i = 0; i < errors.length; i++)
            {
                errors[i] = s.readInt();
            }

            s.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("The file \"parser.dat\" is either missing or corrupted.");
        }
    }
}
