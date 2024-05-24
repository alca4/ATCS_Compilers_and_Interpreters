package codegen;

import java.io.*;

public class Emitter
{
	private PrintWriter out;
    private int label;

	//creates an emitter for writing to a new file with given name
	public Emitter(String outputFileName)
	{
        label = 0;
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	//prints one line of code to file (with non-labels indented)
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	//closes the file.  should be called after all calls to emit.
	public void close()
	{
		out.close();
	}

    public void emitPush()
    {
        emit("subu $sp $sp 4");
        emit("sw $v0 ($sp)");
    }

    public void emitPop()
    {
        emit("lw $t0 ($sp)");
        emit("addu $sp $sp 4");
    }

    public int nextLabel()
    {
        return ++label;
    }
}