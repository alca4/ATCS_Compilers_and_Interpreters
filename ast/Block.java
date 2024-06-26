package ast;

import java.util.List;

import environment.Environment;
import codegen.Emitter;
/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * A list of statements (program)
 */
public class Block extends Statement
{
    /**
     * the list of statements
     */
    private List<Statement> stmts;

    /**
     * Initializes instance fields
     * 
     * @param inputStmts statements to execute
     */
    public Block(List<Statement> inputStmts)
    {
        stmts = inputStmts;
    }

    /**
     * execute statements in order of input
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        for (Statement s : stmts) s.exec(e);
    }

    /**
     * compiles each statement
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        for (Statement s : stmts) s.compile(e);
    }
}
