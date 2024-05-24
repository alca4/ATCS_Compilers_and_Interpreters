package ast;

import environment.Environment;
import codegen.Emitter;

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
     * variables
     */
    private List<String> vars;

    /**
     * Initializes instance fields
     * 
     * @param inputDecl declarations of procedures
     * @param inputStmt statement to execute
     */
    public Program(List<ProcedureDeclaration> inputDecls, Statement inputStmt, List<String> inputVars)
    {
        decls = inputDecls;
        stmt = inputStmt;
        vars = inputVars;
    }

    /**
     * @param e environment which the variables will be in
     */
    public void exec(Environment e)
    {
        for (ProcedureDeclaration p : decls) p.exec(e);
        stmt.exec(e);
    }

    /**
     * compiles the variables and starts at main and ends at li $v0 10
     * 
     * @param e emitter to compile code with
     */
    public void compile(String foutName)
    {
        Emitter e = new Emitter(foutName);
        e.emit(".data");
        e.emit("newline: .asciiz \"\\n\"");

        for (String var : vars) e.emit(var + ": .word 0");

        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");

        stmt.compile(e);

        e.emit("li $v0 10");
        e.emit("syscall");
    }
}
