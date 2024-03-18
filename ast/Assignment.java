package ast;

public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    public Assignment(String inputVar, Expression inputExp)
    {
        var = inputVar;
        exp = inputExp;
    }
}
