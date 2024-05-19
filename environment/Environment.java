/**
 * @author Andrew Liang
 * @version 04.17.24
 * 
 * Stores an environment that can store variables and procedures
 * Operates with a hierarchy
 */

package environment;

import java.util.HashMap;
import java.util.Map;

import ast.ProcedureDeclaration;

public class Environment
{
    /**
     * variables in the environment
     */
    private Map<String, Integer> variables;

    /**
     * procedures declared in the environment, should only be nonempty for root parameter
     */
    private Map<String, ProcedureDeclaration> procedures;

    /**
     * parent environment (the environemnt of the procedure that called the current procedure)
     */
    private Environment parent;

    /**
     * Initializes inputParent
     * 
     * @param inputParent parent environment
     */
    public Environment(Environment inputParent)
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        parent = inputParent;
    }

    /**
     * sets a variable in the current environment
     * 
     * @param varName name of the variable to be set
     * @param value value of the variable
     */
    public void declareVariable(String varName, int value)
    {
        variables.put(varName, value);
    }

    /**
     * sets a variable
     * if it has not been defined in any, define it in the current
     * if it has been only defined in a parent environment, modify the value in the parent environment
     * if it has been defined in this environment, modify it
     * 
     * @param varName name of the variable to be set
     * @param value value of the variable
     */
    public void setVariable(String varName, int value)
    {
        Environment tmp = this;
        while (tmp != null && !tmp.checkVariableInEnv(varName)) tmp = tmp.parent;

        if (tmp == null) declareVariable(varName, value);
        else tmp.declareVariable(varName,value);
    }

    /**
     * @param varName name of variable
     * @return whether the variable exists in this environment
     */
    public boolean checkVariableInEnv(String varName)
    {
        return (variables.get(varName) != null);
    }

    /**
     * @param varName name of variable
     * @return the value of the variable if it was defined in any environment on path to root
     *         0 otherwise
     */
    public int getVariable(String varName)
    {
        if (checkVariableInEnv(varName)) return variables.get(varName);
        else if (parent != null && !checkVariableInEnv(varName)) return parent.getVariable(varName);
        else return 0;
    }

    /**
     * declares a procedure in this environment
     * 
     * @param procName name of procedure 
     * @param value proceduredeclaration 
     */
    public void setProcedure(String procName, ProcedureDeclaration value)
    {
        if (parent != null) parent.setProcedure(procName, value);
        else procedures.put(procName, value);
    }

    /**
     * gets the procedure from the current environment
     * 
     * @param procName name of procedure
     * @return proceduredeclaration of the procedure
     */
    public ProcedureDeclaration getProcedure(String procName)
    {
        if (parent != null) return parent.getProcedure(procName);
        else return procedures.get(procName);
    }
}