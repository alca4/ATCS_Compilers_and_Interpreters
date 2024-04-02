package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles while loops
 */
public class While extends Statement
{
    /**
     * Condition to check when the loop should stop
     */
    private Condition cond;
    /**
     * Expression to evaluate
     */
    private Statement exp;

    /**
     * Initializes instance fields
     * 
     * @param inputCond condition to continue the while loop
     * @param inputState statement to execute during the loop
     */
    public While(Condition inputCond, Statement inputState)
    {
        cond = inputCond;
        exp = inputState;
    }

    /**
     * Evaluates the expression while the condition is true
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        while (cond.eval(e) == 1) exp.exec(e);
    }
}
