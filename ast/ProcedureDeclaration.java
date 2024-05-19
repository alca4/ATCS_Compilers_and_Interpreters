package ast;

import environment.Environment;

import java.util.List;

/**
 * @author Andrew Liang
 * @version 4.8.24
 * 
 * Stores the declaration of a procedure
 */
public class ProcedureDeclaration extends Statement
{
    /**
     * the stored number
     */
    private String name;

    /**
     * procedure body
     */
    public Statement stmt;

    /**
     * parameters to the procedure
     */
    public List<String> params;

    /**
     * Initializes instance fields
     * 
     * @param inputName value of the number
     * @param inputStmt statmement to execute in the procedure
     * @param inputParams list of parameters needed
     */
    public ProcedureDeclaration(String inputName, Statement inputStmt, List<String> inputParams)
    {
        name = inputName;
        stmt = inputStmt;
        params = inputParams;
    }

    /**
     * Declares the procedure in e
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        e.setProcedure(name, this);
    }
}
