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

    protected void filter() throws ParserException, LexerException, IOException
    {
        // Empty body
    }

    private void push(int numstate, ArrayList<Object> listNode, boolean hidden) throws ParserException, LexerException, IOException
    {
        this.nodeList = listNode;

        if(!hidden)
        {
            filter();
        }

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
        push(0, null, true);
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
                        push(this.action[1], list, false);
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
                        PGrammar node1 = (PGrammar) pop().get(0);
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
            case 0: /* reduce AGrammar */
            {
                ArrayList<Object> list = new0();
                push(goTo(0), list, false);
            }
            break;
            case 1: /* reduce AIntLit */
            {
                ArrayList<Object> list = new1();
                push(goTo(1), list, false);
            }
            break;
            case 2: /* reduce AAddOperator */
            {
                ArrayList<Object> list = new2();
                push(goTo(2), list, false);
            }
            break;
            case 3: /* reduce ASubtractOperator */
            {
                ArrayList<Object> list = new3();
                push(goTo(2), list, false);
            }
            break;
            case 4: /* reduce AMultiplyOperator */
            {
                ArrayList<Object> list = new4();
                push(goTo(2), list, false);
            }
            break;
            case 5: /* reduce ADivideOperator */
            {
                ArrayList<Object> list = new5();
                push(goTo(2), list, false);
            }
            break;
        }
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new0() /* reduce AGrammar */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList3 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList2 = pop();
        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PGrammar pgrammarNode1;
        {
            // Block
        PIntLit pintlitNode2;
        POperator poperatorNode3;
        PIntLit pintlitNode4;
        pintlitNode2 = (PIntLit)nodeArrayList1.get(0);
        poperatorNode3 = (POperator)nodeArrayList2.get(0);
        pintlitNode4 = (PIntLit)nodeArrayList3.get(0);

        pgrammarNode1 = new AGrammar(pintlitNode2, poperatorNode3, pintlitNode4);
        }
	nodeList.add(pgrammarNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new1() /* reduce AIntLit */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        PIntLit pintlitNode1;
        {
            // Block
        TIntString tintstringNode2;
        tintstringNode2 = (TIntString)nodeArrayList1.get(0);

        pintlitNode1 = new AIntLit(tintstringNode2);
        }
	nodeList.add(pintlitNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new2() /* reduce AAddOperator */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        POperator poperatorNode1;
        {
            // Block
        TPlus tplusNode2;
        tplusNode2 = (TPlus)nodeArrayList1.get(0);

        poperatorNode1 = new AAddOperator(tplusNode2);
        }
	nodeList.add(poperatorNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new3() /* reduce ASubtractOperator */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        POperator poperatorNode1;
        {
            // Block
        TMinus tminusNode2;
        tminusNode2 = (TMinus)nodeArrayList1.get(0);

        poperatorNode1 = new ASubtractOperator(tminusNode2);
        }
	nodeList.add(poperatorNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new4() /* reduce AMultiplyOperator */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        POperator poperatorNode1;
        {
            // Block
        TStar tstarNode2;
        tstarNode2 = (TStar)nodeArrayList1.get(0);

        poperatorNode1 = new AMultiplyOperator(tstarNode2);
        }
	nodeList.add(poperatorNode1);
        return nodeList;
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    ArrayList<Object> new5() /* reduce ADivideOperator */
    {
        @SuppressWarnings("hiding") ArrayList<Object> nodeList = new ArrayList<Object>();

        @SuppressWarnings("unused") ArrayList<Object> nodeArrayList1 = pop();
        POperator poperatorNode1;
        {
            // Block
        TSlash tslashNode2;
        tslashNode2 = (TSlash)nodeArrayList1.get(0);

        poperatorNode1 = new ADivideOperator(tslashNode2);
        }
	nodeList.add(poperatorNode1);
        return nodeList;
    }



    private static int[][][] actionTable;
/*      {
			{{-1, ERROR, 0}, {4, SHIFT, 1}, },
			{{-1, REDUCE, 1}, },
			{{-1, ERROR, 2}, {5, ACCEPT, -1}, },
			{{-1, ERROR, 3}, {0, SHIFT, 4}, {1, SHIFT, 5}, {2, SHIFT, 6}, {3, SHIFT, 7}, },
			{{-1, REDUCE, 2}, },
			{{-1, REDUCE, 3}, },
			{{-1, REDUCE, 4}, },
			{{-1, REDUCE, 5}, },
			{{-1, ERROR, 8}, {4, SHIFT, 1}, },
			{{-1, REDUCE, 0}, },
        };*/
    private static int[][][] gotoTable;
/*      {
			{{-1, 2}, },
			{{-1, 3}, {8, 9}, },
			{{-1, 8}, },
        };*/
    private static String[] errorMessages;
/*      {
			"expecting: int string",
			"expecting: '+', '-', '*', '/', EOF",
			"expecting: EOF",
			"expecting: '+', '-', '*', '/'",
        };*/
    private static int[] errors;
/*      {
			0, 1, 2, 3, 0, 0, 0, 0, 0, 2, 
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
