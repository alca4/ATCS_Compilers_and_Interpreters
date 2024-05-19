package ast;

import environment.Environment;

import java.util.List;

/**
 * @author Andrew Liang
 * @version 4.8.24
 * 
 * Stores a program
 */
public class Program
{
    /**
     * predefined procedures
     */
    private List<ProcedureDeclaration> decls;

    /**
     * statement to execute
     */
    private Statement stmt;

    /**
     * Initializes instance fields
     * 
     * @param inputDecl declarations of procedures
     * @param inputStmt statement to execute
     */
    public Program(List<ProcedureDeclaration> inputDecls, Statement inputStmt)
    {
        decls = inputDecls;
        stmt = inputStmt;
    }

    /**
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        for (ProcedureDeclaration p : decls) p.exec(e);
        stmt.exec(e);
    }
}
