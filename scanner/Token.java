package scanner;

/*
 * @author: Andrew Liang
 * @version: 01.26.24
 * 
 * Code for a token, which contains a lexeme and its type
 * Five types of tokens are considered:
 *      Number: digit (digit)*
 *      Identifier: letter (letter | digit)*
 *      Operand: '+', '-', '*', '/', '%', '(', ')', ':=', '<=', '>=', '<>'
 *      Separator: ',', ';'
 *      EOF: 'EOF'
 */

public class Token 
{
    private String lexeme;
    private String type;
    
    /*
     * @param inputLexeme: intializes the lexeme
     * @param inputType: initializes the type
     */
    public Token(String inputLexeme, String inputType)
    {
        lexeme = inputLexeme;
        type = inputType;
    }

    /*
     * @return the type and lexeme enclosed in brackets: ex.
     *      [identifier: "hello"]
     *      [number: "1234567890"]
     *      [operand: "%"]
     */
    public String toString()
    {
        return "[" + type + ": \"" + lexeme + "\"]";
    }
}
