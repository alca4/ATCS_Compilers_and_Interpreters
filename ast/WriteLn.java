package ast;

import environment.Environment;

public class WriteLn extends Statement
{
    private Expression exp;

    public WriteLn(Expression inputExp)
    {
        exp = inputExp;
    }

    public void exec(Environment e)
    {
        System.out.println(exp.eval(e));
    }
}
