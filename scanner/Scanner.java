package scanner;

/**
 * @author: Andrew Liang
 * @version: 01.26.24
 * 
 * Code for a class that can tokenize an input character stream
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Provides functionality to separate a stream of characters into tokens.
 */
public class Scanner 
{
    private boolean eof;
    private BufferedReader in;
    private char currentChar;

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     * @throws IOException
     */
    public Scanner(InputStream inStream) throws IOException
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     * @throws IOException
     */
    public Scanner(String inString) throws IOException
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /*
     * ========== RETRIEVING CHARACTERS ==========
     */

    /**
     * Attempts to retrieve the next character in the input stream 
     * while end of file character '.' is not reached
     * @precondition: eof = false
     * @throws IOException
     */
    private void getNextChar() throws IOException
    {
        int c = in.read();
        if (c == -1) eof = true;
        else currentChar = (char) c;
    }

    /**
     * Retrieves the next character if it is equal to an input character
     * @param c: the input character
     * @throws ScanErrorException
     * @throws IOException
     */
    private void eat(char c) throws ScanErrorException, IOException
    {
        if (c == currentChar) getNextChar();
        else throw new ScanErrorException("Expected '" + c + "', found '" + currentChar + "'");
    }

    /**
     * @return true if EOF is not reached, false otherwise
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /*
     * ========== TYPES OF LEXEMES ==========
     */

    /**
     * @param c: input character
     * @return true if the input character is a digit (one of "0123456789"), false otherwise
     */
    public static boolean isDigit(char c) 
    {
        return '0' <= c && c <= '9';
    }

    /**
     * @param c: input character
     * @return true if the input character is whitespace 
     *         (a space, tab, return, or newline), false otherwise
     */
    public static boolean isWhitespace(char c) 
    {
        return " \t\n\r".indexOf(c) != -1;
    }

    /**
     * @param c: input character
     * @return true if the input character is a lowercase or uppercase latin letter, false otherwise
     */
    public static boolean isLetter(char c)
    {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    /**
     * @param c: input character
     * @return true if the input character begins an operand, false otherwise
     */
    public static boolean isOperand(char c)
    {
        return "=+-*/%():<>".indexOf(c) != -1;
    }

    /**
     * @param c: input character
     * @return true if the input character is a separator, false otherwise
     */
    public static boolean isSeparator(char c)
    {
        return ".,;".indexOf(c) != -1;
    }

    /**
     * @param c: input character
     * @return true if the input character is end of file character '.', false otherwise
     */
    public static boolean isEOF(char c)
    {
        return c == '.';
    }

    /*
     * ========== GETTING TOKEN ==========
     */

    /**
     * @return a number given by the regular expression digit (digit)* as a token
     * @throws ScanErrorException
     * @throws IOException
     */
    private Token scanNumber() throws ScanErrorException, IOException
    {
        String num = "";
        while (hasNext() && isDigit(currentChar))
        {
            num += currentChar;
            eat(currentChar);
        }

        return new Token(num, "number");
    }

    /**
     * @return an identifier given by the regular expression letter (letter | digit)* as a token
     * @throws ScanErrorException
     * @throws IOException
     */
    private Token scanIdentifier() throws ScanErrorException, IOException
    {
        String identifier = "";
        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            identifier += currentChar;
            eat(currentChar);
        }
        
        return new Token(identifier, "identifier");
    }

    /**
     * operand is not '/'
     * 
     * @return an operand as a token
     *         operands are '+', '-', '*', '/', '%', '(', ')', ':=', '<=', '>=', '<>'
     * @throws ScanErrorException 
     * @throws IOException
     */
    private Token scanOperand() throws ScanErrorException, IOException
    {
        String ret = String.valueOf(currentChar);
        char previousChar = currentChar;
        eat(currentChar);
        if (previousChar == '<')
        {
            if (currentChar == '=' || currentChar == '>') 
            {
                ret += String.valueOf(currentChar);
                eat(currentChar);
            }
        }
        else if (previousChar == '>')
        {
            if (currentChar == '=') 
            {
                ret += String.valueOf(currentChar);
                eat(currentChar);
            }
        }
        else if (previousChar == ':')
        {
            if (currentChar == '=') 
            {
                ret += String.valueOf(currentChar);
                eat(currentChar);
            }
            else throw new ScanErrorException(": is not an operand");
        }
        return new Token(ret, "operand");
    }

    /**
     * @return a separator (comma and semicolon) as a token
     * @throws ScanErrorException
     * @throws IOException
     */
    private Token scanSeparator() throws ScanErrorException, IOException
    {   
        Token ret = new Token(String.valueOf(currentChar), "separator");
        eat(currentChar);
        return ret;
    }

    /**
     * @return end of file token ("EOF")
     * @throws ScanErrorException
     * @throws IOException
     */
    private Token scanEOF() throws ScanErrorException, IOException
    {
        eat(currentChar);
        eof = true;
        return new Token("EOF", "EOF");
    }

    /**
     * @precondition: previous 2 characters were '//' if singleLine is true
     *                                           '/*' if singleLine is false
     * singleLine is true: upon scanning "//", removes every character until '\n', deletes inline comment
     * singleLine is false: upon scanning "/*", remove every character until star and slash
     * @param singleLine: true if single 
     * @throws ScanErrorException
     * @throws IOException
     */
    private void removeComment(boolean singleLine) throws ScanErrorException, IOException
    {
        if (singleLine) 
        {
            while (currentChar != '\n') eat(currentChar);
            eat(currentChar);
        }
        else 
        {
            char previousChar = currentChar;
            eat(currentChar);
            while (!(previousChar == '*' && currentChar == '/')) 
            {
                previousChar = currentChar;
                eat(currentChar);
            }
            eat(currentChar);
        }
    }

    /**
     * removes all whitespace between currentChar and the next token
     * identifies the type of token based on its first character
     * @return the next token
     * @throws ScanErrorException
     * @throws IOException
     */
    public Token nextToken() throws ScanErrorException, IOException
    {
        while (hasNext())
        {
            while (hasNext() && isWhitespace(currentChar)) eat(currentChar);

            if (!hasNext()) return new Token("EOF", "EOF");
            else if (isDigit(currentChar)) return scanNumber();
            else if (isLetter(currentChar)) return scanIdentifier();
            else if (isOperand(currentChar)) 
            {
                if (currentChar == '/')
                {
                    eat(currentChar);
                    if (currentChar == '/' || currentChar == '*') 
                    {
                        if (currentChar == '/') removeComment(true);
                        else removeComment(false);
                    }
                    else return new Token("/", "operand");
                }
                else return scanOperand();
            }
            else if (isSeparator(currentChar)) return scanSeparator();
            else throw new ScanErrorException("Unrecognized token '" + currentChar + "'");
        }
        return new Token("EOF", "EOF");
    }

    /**
     * tokenizes the input stream into is respective tokens, ending with "EOF" as end of file token
     * @return a list of tokens
     * @throws ScanErrorException
     * @throws IOException
     */
    public ArrayList<Token> tokenize() throws ScanErrorException, IOException
    {
        ArrayList<Token> tokenStream = new ArrayList<>();
        while (hasNext()) tokenStream.add(nextToken());

        return tokenStream;
    }

    /**
     * Tests the scanner, reads input from a file or standard input depending on which line is commented
     * @throws ScanErrorException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException, IOException
    {
        // Scanner sc = new Scanner(new FileInputStream("scanner/scannerTestAdvanced.txt"));
        Scanner sc = new Scanner(new FileInputStream("scanner/mytest.txt"));
        // Scanner sc = new Scanner(System.in);
        
        ArrayList<Token> tokens = sc.tokenize();
        for (Token t : tokens) System.out.println(t);
    }
}