package ast;

import environment.Environment;

import java.util.List;

/**
 * @author Andrew Liang
 * @version 4.8.24
 * 
 * Stores the call of a procedure
 */
public class ProcedureCall extends Expression
{
    /**
     * the stored number
     */
    private String name;

    /**
     * stores inputs to the procedure
     */
    private List<Expression> params;

    /**
     * Initializes instance fields
     * 
     * @param inputName value of the number
     * @param inputParams parameters to specific function call
     */
    public ProcedureCall(String inputName, List<Expression> inputParams)
    {
        name = inputName;
        params = inputParams;
    }

    /**
     * Creates a new environment, initializes parameters and return value in that environment
     * 
     * @param e environment which the variables will be in
     * @return the return value of the procedure
     */
    public int eval(Environment e)
    {
        ProcedureDeclaration d = e.getProcedure(name);

        Environment e2 = new Environment(e);
        for (int i = 0; i < params.size(); i++) 
        {
            Assignment a = new Assignment(d.params.get(i), params.get(i));
            a.exec(e2);
        }

        Assignment retValue = new Assignment(name, new Number(0));
        retValue.exec(e2);
        d.stmt.exec(e2);

        return e2.getVariable(name);
    }
}
