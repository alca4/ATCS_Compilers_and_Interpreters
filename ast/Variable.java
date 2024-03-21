package ast;

import environment.Environment;

public class Variable extends Expression
{
    private String name;

    public Variable(String inputName)
    {
        name = inputName;
    }

    public int eval(Environment e)
    {
        return e.getVariable(name);
    }
}
