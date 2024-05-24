package ast;

import environment.Environment;
import codegen.Emitter;

/**
 * @author Andrew Liang
 * @version 3.21.24
 * 
 * Handles while loops
 */
public class While extends Statement
{
    /**
     * Condition to check when the loop should stop
     */
    private Condition cond;
    /**
     * Statement to evaluate
     */
    private Statement stmt;

    /**
     * Initializes instance fields
     * 
     * @param inputCond condition to continue the while loop
     * @param inputState statement to execute during the loop
     */
    public While(Condition inputCond, Statement inputState)
    {
        cond = inputCond;
        stmt = inputState;
    }

    /**
     * Evaluates the expression while the condition is true
     * 
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        while (cond.eval(e) == 1) stmt.exec(e);
    }

    /**
     * creates a label to do the loop in
     * compiles everything inside the loop in this label
     * creates another label for after the loop
     * 
     * @param e emitter to compile code with
     */
    public void compile(Emitter e)
    {
        String startLabel = "startwhile" + e.nextLabel();
        String endLabel = "endwhile" + e.nextLabel();

        e.emit("j " + startLabel);
        e.emit(startLabel + ":");

        cond.compile(e, endLabel);

        stmt.compile(e);
        e.emit("j " + startLabel);
        e.emit(endLabel + ":");
    }
}
