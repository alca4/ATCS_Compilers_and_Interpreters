package ast;

import environment.Environment;

public class While extends Statement
{
    private Condition cond;
    private Statement exp;

    public While(Condition inputCond, Statement inputState)
    {
        cond = inputCond;
        exp = inputState;
    }

    public void exec(Environment e)
    {
        while (cond.eval(e) == 1) exp.exec(e);
    }
}
