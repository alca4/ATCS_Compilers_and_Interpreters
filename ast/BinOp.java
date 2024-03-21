package ast;

import environment.Environment;

public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    public BinOp(String inputOp, Expression inputExp1, Expression inputExp2)
    {
        op = inputOp;
        exp1 = inputExp1;
        exp2 = inputExp2;
    }

    public int eval(Environment e)
    {
        int v1 = exp1.eval(e);
        int v2 = exp2.eval(e);
        
        if (op.equals("+")) return v1 + v2;
        else if (op.equals("-")) return v1 - v2;
        else if (op.equals("*")) return v1 * v2;
        else if (op.equals("/")) return v1 / v2;
        else return v1 % v2;
    }
}
