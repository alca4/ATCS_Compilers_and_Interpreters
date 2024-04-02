package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Stores a variable name whose value is stored in the environment
 * value can be accessed freely
 */
public class Variable extends Expression
{
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
}
