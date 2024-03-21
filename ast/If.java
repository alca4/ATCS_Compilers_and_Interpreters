package ast;

import environment.Environment;

public class If extends Statement
{
    private Condition cond;
    private Statement exp;

    public If(Condition inputCond, Statement inputState)
    {
        cond = inputCond;
        exp = inputState;
    }

    public void exec(Environment e)
    {
        if (cond.eval(e) == 1) exp.exec(e);
    }
}
