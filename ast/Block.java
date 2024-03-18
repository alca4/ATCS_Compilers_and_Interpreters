package ast;

import java.util.List;

public class Block extends Statement
{
    private List<Statement> stmts;

    public Block(List<Statement> inputStmts)
    {
        stmts = inputStmts;
    }
}
