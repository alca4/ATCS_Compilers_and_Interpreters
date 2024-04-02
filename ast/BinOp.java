package ast;

import environment.Environment;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles arithmetic operations to variables
 */
public class BinOp extends Expression
{
    /**
     * Operation: +, -, *, /, or mod
     */
    private String op;
    /**
     * LHS expression
     */
    private Expression exp1;
    /**
     * RHS expression
     */
    private Expression exp2;

    /**
     * Initializes instance fields
     * 
     * @param inputOp type of binary operation
     * @param inputExp1 exression of lhs
     * @param inputExp2 exression of rhs
     */
    public BinOp(String inputOp, Expression inputExp1, Expression inputExp2)
    {
        op = inputOp;
        exp1 = inputExp1;
        exp2 = inputExp2;
    }

    /**
     * Perform operation
     * @param e environment which the variables will be in
     * @return result of operation
     */
    public int eval(Environment e)
    {
        int v1 = exp1.eval(e);
        int v2 = exp2.eval(e);
        
        if (op.equals("+")) return v1 + v2;
        else if (op.equals("-")) return v1 - v2;
        else if (op.equals("*")) return v1 * v2;
        else if (op.equals("/")) return v1 / v2;
        else return v1 % v2;
    }
}
