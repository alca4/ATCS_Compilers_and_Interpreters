package ast;

import environment.Environment;

public class For extends Statement
{
    private Assignment init;
    private Condition cond;
    private Statement exp;
    private Assignment increment;

    public For(Assignment inputInit, Assignment inputIncrement, Condition inputCond, Statement inputState)
    {
        init = inputInit;
        increment = inputIncrement;
        cond = inputCond;
        exp = inputState;
    }

    public void exec(Environment e)
    {
        init.exec(e);
        while (cond.eval(e) == 1)
        {
            exp.exec(e);
            increment.exec(e);
        }
    }
}