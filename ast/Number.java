package ast;

import environment.Environment;

public class Number extends Expression
{
    private int value;

    public Number(int inputValue)
    {
        value = inputValue;
    }

    public int eval(Environment e)
    {
        return value;
    }
}
