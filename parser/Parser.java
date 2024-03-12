package parser;

/**
 * @author Andrew Liang
 * @version 03.04.24
 * 
 * Parses Pascal integer arithmetic code
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import scanner.ScanErrorException;
import scanner.Scanner;
import scanner.Token;

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
     * stores variable names and their value
     */
    private Map<String, Integer> variables;

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
        variables = new HashMap<String, Integer>();
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
    private int parseNumber() throws IllegalArgumentException, ScanErrorException, IOException
    {
        int ret = Integer.parseInt(currentToken.lexeme);
        eat(currentToken);
        return ret;
    }

    /**
     * Defined by the grammar
     * S -> "WRITELN"(E) | "BEGIN" S2 "END"
     * S2 -> S S2 | epsilon
     * 
     * Evaluates a statement (a program or a single line)
     * 
     * @precondition currentToken is "WRITELN"
     * @throws IllegalArgumentException
     * @throws ScanErrorException
     * @throws IOException
     */
    public void parseStatement() throws IllegalArgumentException, ScanErrorException, IOException
    {
        if (currentToken.equals(new Token("BEGIN", "identifier")))
        {
            eat(currentToken);
            while (!currentToken.equals(new Token("END", "identifier"))) parseStatement();
            eat(currentToken);
        }
        else if (currentToken.equals(new Token("WRITELN", "identifier")))
        {
            eat(currentToken);
            eat(new Token("(", "operand"));
            System.out.println(parseExpression());
            eat(new Token(")", "operand"));
        }
        else 
        {
            String varName = currentToken.lexeme;
            eat(currentToken);
            eat(new Token(":=", "operand"));
            variables.put(varName, parseExpression());
        }
        eat(new Token(";", "separator"));
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
    public int parseFactor() throws IllegalArgumentException, ScanErrorException, IOException
    {
        int ret;
        if (currentToken.equals(new Token("(", "operand")))
        {
            eat(currentToken);
            ret = parseExpression();
            eat(new Token(")", "operand"));
        }
        else if (currentToken.equals(new Token("-", "operand")))
        {
            eat(currentToken);
            ret = -parseFactor();
        }
        else if (currentToken.type.equals("identifier")) 
        {
            ret = variables.get(currentToken.lexeme);
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
    public int parseTerm() throws IllegalArgumentException, ScanErrorException, IOException
    {
        int ret = parseFactor();
        while (currentToken.equals(new Token("*", "operand")) ||
               currentToken.equals(new Token("/", "operand")) ||
               currentToken.equals(new Token("mod", "identifier")))
        {            
            if (currentToken.equals(new Token("*", "operand"))) 
            {
                eat(currentToken);
                ret *= parseFactor();
            }
            else if (currentToken.equals(new Token("/", "operand"))) 
            {
                eat(currentToken);
                ret /= parseFactor();
            }
            else 
            {
                eat(currentToken);
                ret %= parseFactor();
            }
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
    public int parseExpression() throws IllegalArgumentException, ScanErrorException, IOException
    {
        int ret = parseTerm();
        while (currentToken.equals(new Token("+", "operand")) ||
               currentToken.equals(new Token("-", "operand")))
        {
            if (currentToken.equals(new Token("+", "operand")))
            {
                eat(currentToken);
                ret += parseTerm();
            }
            else
            {
                eat(currentToken);
                ret -= parseTerm();
            }
        }

        return ret;
    }

    public static void main(String[] args) throws ScanErrorException, IOException
    {
        Parser p = new Parser(new Scanner(System.in));

        // System.out.println(p.parseExpression());
        p.parseStatement();
    }
}