package ast;

import java.util.List;

import environment.Environment;

public class Block extends Statement
{
    private List<Statement> stmts;

    public Block(List<Statement> inputStmts)
    {
        stmts = inputStmts;
    }

    public void exec(Environment e)
    {
        for (Statement s : stmts) s.exec(e);
    }
}
