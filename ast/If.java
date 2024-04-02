package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles if statements
 */
public class If extends Statement
{
    /**
     * condition to initiate the if
     */
    private Condition cond;
    /**
     * statement to execute if the condition is true
     */
    private Statement stmt;

    /**
     * Initializes instance fields
     * 
     * @param inputCond condition to execute the if
     * @param inputState statement to execute in the if statement
     */
    public If(Condition inputCond, Statement inputState)
    {
        cond = inputCond;
        stmt = inputState;
    }

    /**
     * Evaluates the condition, if true, execute statement 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        if (cond.eval(e) == 1) stmt.exec(e);
    }
}
