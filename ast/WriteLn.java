package ast;

public class WriteLn extends Statement
{
    private Expression exp;

    public WriteLn(Expression inputExp)
    {
        exp = inputExp;
    }
}
