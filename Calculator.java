package calculator;

import java.math.BigInteger;
import java.util.*;

public class Calculator {

    CalculatorDatabase database = new CalculatorDatabase();
    Command command = new Command();


    public Calculator() {
        command.setDatabase(database);
    }

    public void assignment(String request) {

        String[] requestArray = request.split("[\\s]*[=][\\s]*");
        String var = requestArray[0];

        try {
            if (database.containsVariable(var)) { //reassigning variable
                if (database.containsVariable(requestArray[1])) {
                    database.reassignVariable(var, requestArray[1]);
                } else {
                    database.reassignVariable(var, (new BigInteger(requestArray[1]).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1 || new BigInteger(requestArray[1]).compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1) ? new BigInteger(requestArray[1]) : Integer.parseInt(requestArray[1]));
                }
            } else { //new variable
                if (database.containsVariable(requestArray[1])) {
                    database.addVariable(var, requestArray[1]);
                } else {
                    database.addVariable(var, (new BigInteger(requestArray[1]).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1 || new BigInteger(requestArray[1]).compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1) ? new BigInteger(requestArray[1]) : Integer.parseInt(requestArray[1]));
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            database.identifierError();
        }
    }

    public void calculate(String request) {
        List<String> postfix = getPostfixExpression(request);
        ArrayDeque<Number> numberStack = new ArrayDeque<>();

        for (String x: postfix) {
            if (x.matches("[-]?[\\d]+")) {
                numberStack.push((new BigInteger(x).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1 || new BigInteger(x).compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) ==-1) ? new BigInteger(x) : Integer.parseInt(x));
            } else if (x.matches(String.valueOf(PatternChecker.getOperatorPattern()))) {
                BigInteger numberOnTop = new BigInteger(String.valueOf(numberStack.pop()));
                BigInteger numberBelow = new BigInteger(String.valueOf(numberStack.pop()));
                switch (x) {
                    case "^" -> numberStack.push(numberBelow.pow(numberOnTop.intValue()));
                    case "/" -> numberStack.push(numberBelow.divide(numberOnTop));
                    case "*" -> numberStack.push(numberBelow.multiply(numberOnTop));
                    case "+" -> numberStack.push(numberBelow.add(numberOnTop));
                    case "-" ->  numberStack.push(numberBelow.subtract(numberOnTop));
                }
            } else {
                System.out.println("Invalid Expression");
                return;
            }
        }
        Number result = numberStack.pop();
        System.out.println(numberStack.isEmpty() ? result : "Invalid Expression");
    }

    private Number getValueAccordingToKeyOrGetInteger(String numberToOperateOn) {
        if (database.containsVariable(numberToOperateOn)) {
            return database.getValue(numberToOperateOn);
        } else if (numberToOperateOn.matches("[-]?[\\d]+")) {
            return  (new BigInteger(numberToOperateOn).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1 || new BigInteger(numberToOperateOn).compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) ==-1) ? new BigInteger(numberToOperateOn) : Integer.parseInt(numberToOperateOn);
        } return null;
    }

    private int operationPrecedence(String operation) {
        return switch(operation) {
            case "^" -> 0;
            case "*","/" -> -1;
            case "+","-" -> -2;
            default -> -3;
        };
    }

    private List<String> getPostfixExpression(String request) {

        request = request.replaceAll("[(]", " ( ").replaceAll("[)]", " ) ").trim();
        String[] requestArray = request.split("[\\s]+");
        List<String> postfix = new ArrayList<>();
        ArrayDeque<String> postfixOperators = new ArrayDeque<>();

        for (String x : requestArray) {
            if (x.matches("[-]?[\\d]+|%s".formatted(String.valueOf(PatternChecker.getVariablePattern())))) {
                postfix.add(String.valueOf(getValueAccordingToKeyOrGetInteger(x)));
            } else if (postfixOperators.isEmpty() && x.matches(String.valueOf(PatternChecker.getOperatorPattern()))) {
                postfixOperators.push(PatternChecker.getValidOperator(x));
            } else if (x.equals("(")) {
                postfixOperators.push(x);
            } else if (x.equals(")")) {
                while (!postfixOperators.isEmpty() && !postfixOperators.peek().equals("(")) {
                    postfix.add(postfixOperators.pop());
                }
                postfixOperators.pop();
            } else if (operationPrecedence(x) <= operationPrecedence(postfixOperators.peek())) {
                while (!postfixOperators.isEmpty() && operationPrecedence(x) <= operationPrecedence(postfixOperators.peek()) && !postfixOperators.peek().equals("(")) {
                    postfix.add(postfixOperators.pop());
                }
                postfixOperators.push(PatternChecker.getValidOperator(x));
            } else if (operationPrecedence(x) > operationPrecedence(postfixOperators.peek())) {
                postfixOperators.push(PatternChecker.getValidOperator(x));
            }
        }

        while (!postfixOperators.isEmpty()) {
            postfix.add(postfixOperators.pop());
        }

        return postfix;
    }
}

