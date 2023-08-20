package calculator;

import java.util.Map;
import java.util.TreeMap;

public class CalculatorDatabase {

    //key, value = variable name, integer value(technically T)
    private Map<String, Number> variables = new TreeMap<>();

    public void addVariable(String key, Number value) {
        variables.put(key, value);
    }

    public void addVariable(String key, String targetKey) {
        variables.put(key, variables.get(targetKey));
    }

    public void reassignVariable(String key,String keyWithTargetValue) {
        variables.replace(key, variables.get(keyWithTargetValue));
    }

    public void reassignVariable(String key, Number targetValue) {
        variables.replace(key, targetValue);
    }

    public Number getValue(String key) {
        return variables.get(key);
    }

    public boolean isValidVariableName(String key) {
        return PatternChecker.getVariablePattern().matcher(key).matches();
    }

    public boolean containsVariable(String key) {
        return variables.containsKey(key);
    }

    public void identifierError() {
        System.out.println("Invalid identifier");
    }

    public void invalidVariableNameError() {
        System.out.println("Unknown variable");
    }

    public void getAllVariables() {
        variables.entrySet().forEach(System.out::println);
    }
}