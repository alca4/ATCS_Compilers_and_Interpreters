package environment;

import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private Map<String, Integer> variables;

    public Environment()
    {
        variables = new HashMap<String, Integer>();
    }

    public void setVariable(String varName, int value)
    {
        variables.put(varName, value);
    }

    public int getVariable(String varName)
    {
        return variables.get(varName);
    }
}