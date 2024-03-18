package ast;

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
}
