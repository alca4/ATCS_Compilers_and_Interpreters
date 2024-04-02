package ast;

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
}
