package ast;

import environment.Environment;
import codegen.Emitter;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Stores a variable name whose value is stored in the environment
 * value can be accessed freely
 */
public class Variable extends Expression
{
    /**
     * name of variable
     */
    private String name;

    /**
     * Initializes instance fields
     * 
     * @param inputName name of the variable
     */
    public Variable(String inputName)
    {
        name = inputName;
    }

    /**
     * @param e environment which the variables will be in
     * @return value of the variable
     */
    public int eval(Environment e)
    {
        return e.getVariable(name);
    }

    /**
     * retrieves value of variable to $t0
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        e.emit("la $t0 " + name);
        e.emit("lw $v0 ($t0)");
    }
}
