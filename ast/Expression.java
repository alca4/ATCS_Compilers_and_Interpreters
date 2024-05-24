package ast;

import javax.management.RuntimeErrorException;

import codegen.Emitter;
import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles expressions (which have an integer value)
 */
public abstract class Expression 
{
    public abstract int eval(Environment e);
    public void compile(Emitter e)
    {
        throw new RuntimeException("implement me pls");
    }
}
