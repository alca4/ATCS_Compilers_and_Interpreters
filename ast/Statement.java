package ast;

import javax.management.RuntimeErrorException;


import codegen.Emitter;
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
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me pls");
    }
}
