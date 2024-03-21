package ast;

import environment.Environment;

public abstract class Expression 
{
    public abstract int eval(Environment e);
}
