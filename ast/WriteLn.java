package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles print statements
 */
public class WriteLn extends Statement
{
    /**
     * expression, prints the value
     */
    private Expression exp;

    /**
     * Initializes instance fields
     * 
     * @param inputExp expression whose value should be printed out
     */
    public WriteLn(Expression inputExp)
    {
        exp = inputExp;
    }

    /**
     * prints the value of the given expression
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        System.out.println(exp.eval(e));
    }
}
