package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles for loops
 */
public class For extends Statement
{
    /**
     * initialization of loop variable
     */
    private Assignment init;
    /**
     * condition to stop the loop
     */
    private Condition cond;
    /**
     * statement to execute in the loop
     */
    private Statement stmt;
    /**
     * mode of incrementation
     */
    private Assignment increment;

    /**
     * Initializes instance fields
     * 
     * @param inputInit initialization of loop variable
     * @param inputIncrement how to increment the loop variable
     * @param inputCond condition to end the for loop
     * @param inputState statement to execute in the for loop
     */
    public For(Assignment inputInit, Assignment inputIncrement, Condition inputCond, Statement inputState)
    {
        init = inputInit;
        increment = inputIncrement;
        cond = inputCond;
        stmt = inputState;
    }

    /**
     * first initializes loop variable
     * while the final condition is not met, executes the contents of the loop
     * and updates the loop variable
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        init.exec(e);
        while (cond.eval(e) == 1)
        {
            stmt.exec(e);
            increment.exec(e);
        }
    }
}