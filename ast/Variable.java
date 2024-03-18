package ast;

public class Variable extends Expression
{
    private String name;

    public Variable(String inputName)
    {
        name = inputName;
    }
}
