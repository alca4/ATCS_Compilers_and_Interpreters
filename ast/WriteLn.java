package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles print statements
 */
public class WriteLn extends Statement
{
    /**
     * expression, prints the value
     */
    private Expression exp;

    /**
     * Initializes instance fields
     * 
     * @param inputExp expression whose value should be printed out
     */
    public WriteLn(Expression inputExp)
    {
        exp = inputExp;
    }

    /**
     * prints the value of the given expression
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        System.out.println(exp.eval(e));
    }

    /**
     * prints out number
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");

        e.emit("li $v0 4");
        e.emit("la $a0 newline");
        e.emit("syscall");
    }
}
