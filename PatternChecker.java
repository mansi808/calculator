package calculator;

import java.util.regex.Pattern;

//no trailing spaces and starting spaces are checked in patterns

public class PatternChecker {

    private static final Pattern variablePattern = Pattern.compile("[A-Za-z]+");
    private static final Pattern assignmentPattern = Pattern.compile("%s[\\s]*[=][\\s]*[-]?(%s|[\\d]+)".formatted(String.valueOf(variablePattern),String.valueOf(variablePattern)));
    private static final Pattern operatorPattern = Pattern.compile("[+-]+|[*/^]");

    public static String getValidOperator(String operator) {
        //1. replace all multiple adjacent minus and plus with one minus
        //2. replace all two or more adjacent plus with one plus
        //3. replace all two adjacent minus with plus

        while (operator.length() > 1 || (operator.contains("+") && operator.contains("-"))) {
            operator = operator.trim().replaceAll("([+][-])|([-][+])","-")
                    .replaceAll("([+]{2,})","+")
                    .replaceAll("([-]{2})","+");
        } return operator;
    }

    public static Pattern getAssignmentPattern() {
        return assignmentPattern;
    }

    public static Pattern getVariablePattern() {
        return variablePattern;
    }

    public static Pattern getOperatorPattern() {
        return operatorPattern;
    }

}
