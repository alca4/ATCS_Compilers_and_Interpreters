package ast;

import environment.Environment;

public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    public Assignment(String inputVar, Expression inputExp)
    {
        var = inputVar;
        exp = inputExp;
    }

    public void exec(Environment e)
    {
        e.setVariable(var, exp.eval(e));
    }
}
