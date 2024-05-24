package ast;

import environment.Environment;
import codegen.Emitter;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Stores numbers
 */
public class Number extends Expression
{
    /**
     * the stored number
     */
    private int value;

    /**
     * Initializes instance fields
     * 
     * @param inputValue value of the number
     */
    public Number(int inputValue)
    {
        value = inputValue;
    }

    /**
     * @param e environment which the variables will be in
     * @return the value
     */
    public int eval(Environment e)
    {
        return value;
    }

    /**
     * loads variable to $v0
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
