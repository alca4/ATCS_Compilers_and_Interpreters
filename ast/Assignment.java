package ast;

import environment.Environment;
import codegen.Emitter;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles assignment of variables to values
 */
public class Assignment extends Statement
{
    /**
     * variable name
     */
    private String var;
    /**
     * expression, value assigned to variable
     */
    private Expression exp;

    /**
     * Initializes instance fields
     * 
     * @param inputVar variable name
     * @param inputExp expression, its value is the variable's initial value
     */
    public Assignment(String inputVar, Expression inputExp)
    {
        var = inputVar;
        exp = inputExp;
    }

    /**
     * assign the variable the value of the expression in the environment
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        e.setVariable(var, exp.eval(e));
    }

    /**
     * assigns a value to a variable by storing the value to $t0 first
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t0 " + var);
        e.emit("sw $v0 ($t0)");
    }
}
