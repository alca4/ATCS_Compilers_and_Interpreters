package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Andrew Liang
 * @version 03.04.24
 * 
 * Parses Pascal integer arithmetic code
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import scanner.ScanErrorException;
import scanner.Scanner;
import scanner.Token;

import ast.*;
import environment.*;

/**
 * Parses Pascal integer arithmetic code
 * 
 * Can handle addition, subtraction, multiplication, division, mod, parentheses
 * Can support integer variables
 */
public class Parser
{
    /**
     * The scanner, generates the token stream
     */
    private Scanner scanner;
    /**
     * Token currently examining
     */
    private Token currentToken;

    /**
     * Initializes the scanner, the current token, and the map storing variables.
     * 
     * @param inputSc a scanner that will feed the parser with a token stream
     * @throws ScanErrorException
     * @throws IOException
     */
    public Parser(Scanner inputSc) throws ScanErrorException, IOException
    {
        scanner = inputSc;
        currentToken = scanner.nextToken();
    }

    /**
     * Advances to next token in token stream if current token matches an expected token
     * Throws IllegalArgumentException if current does not match expected
     * 
     * @param expectedToken currentToken should be equal to expectedToken
     * @throws ScanErrorException
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private void eat(Token expectedToken) throws ScanErrorException, IOException, IllegalArgumentException
    {
        if (expectedToken.equals(currentToken)) currentToken = scanner.nextToken();
        else throw new IllegalArgumentException("Expected \"" + expectedToken.lexeme + "\", found \"" + currentToken.lexeme + "\"");
    }

    /**
     * @precondition currentToken.type() == "number"
     * @postcondition token stream is advanced by 1
     * @return value of parsed integer
     * @throws IOException 
     * @throws ScanErrorException 
     * @throws IllegalArgumentException 
     */
    private ast.Number parseNumber() throws IllegalArgumentException, ScanErrorException, IOException
    {
        ast.Number ret = new ast.Number(Integer.parseInt(currentToken.lexeme));
        eat(currentToken);
        return ret;
    }

    /**
     * C -> E comparator E
     * 
     * comparators: <, >, =, <>, <=, >=
     * @return condition class containing condition
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public Condition parseCondition() throws IllegalArgumentException, ScanErrorException, IOException
    {
        Expression e1 = parseExpression();
        assert (currentToken.equals(new Token("<", "Operand")) ||
                currentToken.equals(new Token(">", "Operand")) ||
                currentToken.equals(new Token("<=", "Operand")) ||
                currentToken.equals(new Token(">=", "Operand")) ||
                currentToken.equals(new Token("=", "Operand")) ||
                currentToken.equals(new Token("<>", "Operand")));
        String op = currentToken.lexeme;
        eat(currentToken);
        return new Condition(op, e1, parseExpression());
    }

    /**
     * Defined by the grammar
     * S -> "WRITELN"(E) | "BEGIN" S2 "END" | Identifire := Expression
     * S2 -> S S2 | epsilon
     * 
     * Evaluates a statement (a program or a single line)
     * 
     * @precondition currentToken is "WRITELN"
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public Statement parseStatement() throws IllegalArgumentException, ScanErrorException, IOException
    {
        Statement ret;
        if (currentToken.equals(new Token("BEGIN", "identifier")))
        {
            eat(currentToken);
            List<Statement> block = new ArrayList<Statement>();

            while (!currentToken.equals(new Token("END", "identifier"))) 
                block.add(parseStatement());
            
            ret = new Block(block);
            eat(currentToken);
            eat(new Token(";", "separator"));
        }
        else if (currentToken.equals(new Token("WRITELN", "identifier")))
        {
            eat(currentToken);
            eat(new Token("(", "operand"));
            ret = new WriteLn(parseExpression());
            eat(new Token(")", "operand"));
            eat(new Token(";", "separator"));
        }
        else if (currentToken.equals(new Token("READLN", "identifier")))
        {
            eat(currentToken);
            eat(new Token("(", "operand"));
            assert(currentToken.type.equals("identifier"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int val = Integer.parseInt(reader.readLine());
            System.out.println("read values is " + val);
            ret = new Assignment(currentToken.lexeme, new ast.Number(val));
            eat(currentToken);
            eat(new Token(")", "operand"));
            eat(new Token(";", "separator"));
        }
        else if (currentToken.equals(new Token("IF", "identifier")))
        {
            eat(currentToken);
            Condition c = parseCondition();
            eat(new Token("THEN", "identifier"));
            ret = new If(c, parseStatement());
        }
        else if (currentToken.equals(new Token("WHILE", "identifier")))
        {
            eat(currentToken);
            Condition c = parseCondition();
            eat(new Token("DO", "identifier"));
            ret = new While(c, parseStatement());
        }
        else if (currentToken.equals(new Token("FOR", "identifier")))
        {
            eat(currentToken);
            
            assert(currentToken.type.equals("identifier"));
            String loopVariable = currentToken.lexeme;
            eat(currentToken);
            eat(new Token(":=", "operand"));
            Expression initValue = parseExpression();
            Assignment init = new Assignment(loopVariable, initValue);

            int dir = 1;
            if (currentToken.lexeme.equals("DOWN")) 
            {
                dir = -1;
                eat(currentToken);
            }
            eat(new Token("TO", "identifier"));
            Assignment inc = new Assignment(loopVariable, 
                                            new BinOp("+", 
                                            new Variable(loopVariable), new ast.Number(dir)));
            
            Expression finalValue = parseExpression();
            String compareOp = dir > 0 ? "<=" : ">=";
            Condition c = new Condition(compareOp, new Variable(loopVariable), finalValue);

            eat(new Token("DO", "identifier"));
            ret = new For(init, inc, c, parseStatement());
        }
        else 
        {
            String varName = currentToken.lexeme;
            eat(currentToken);
            eat(new Token(":=", "operand"));
            ret = new Assignment(varName, parseExpression());
            eat(new Token(";", "separator"));
        }
        return ret;
    }

    /**
     * Defined by the grammar:
     * F -> (E)
     *   -> -F
     *   -> I
     * I is an integer
     * 
     * @return integer value of the factor
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public Expression parseFactor() throws IllegalArgumentException, ScanErrorException, IOException
    {
        Expression ret;
        if (currentToken.equals(new Token("(", "operand")))
        {
            eat(currentToken);
            ret = parseExpression();
            eat(new Token(")", "operand"));
        }
        else if (currentToken.equals(new Token("-", "operand")))
        {
            eat(currentToken);
            ret = new BinOp("-", new ast.Number(0), parseFactor());
        }
        else if (currentToken.type.equals("identifier")) 
        {
            ret = new Variable(currentToken.lexeme);
            eat(currentToken);
        }
        else ret = parseNumber();

        return ret;
    }

    /**
     * Defined by the grammar:
     * T  -> FT'
     * T' -> *FT' | /FT' | F
     * 
     * @return integer value of the term
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public Expression parseTerm() throws IllegalArgumentException, ScanErrorException, IOException
    {
        Expression ret = parseFactor();
        while (currentToken.equals(new Token("*", "operand")) ||
               currentToken.equals(new Token("/", "operand")) ||
               currentToken.equals(new Token("mod", "identifier")))
        {
            String op = currentToken.lexeme;
            eat(currentToken);
            ret = new BinOp(op, ret, parseFactor());
        }

        return ret;
    }

    /**
     * Defined by the grammar:
     * E -> TE'
     * E' -> +TE' | -TE' | T
     * 
     * Handles repeated addition and subtraction
     * 
     * @return integer value of the term
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public Expression parseExpression() throws IllegalArgumentException, ScanErrorException, IOException
    {
        Expression ret = parseTerm();

        while (currentToken.equals(new Token("+", "operand")) ||
               currentToken.equals(new Token("-", "operand")))
        {
            String op = currentToken.lexeme;
            eat(currentToken);
            ret = new BinOp(op, ret, parseTerm());
        }

        return ret;
    }

    public static void main(String[] args) throws ScanErrorException, IOException
    {
        FileInputStream inStream = new FileInputStream(new File("parser/parser_test_0.txt"));
        Parser p = new Parser(new Scanner(inStream));
        p.parseStatement().exec(new Environment());
    }
}