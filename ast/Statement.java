package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handes statements: writeln, assignment, if, for, while, binary operations
 */
public abstract class Statement 
{
    public abstract void exec(Environment e);
}
