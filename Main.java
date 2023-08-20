package calculator;

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String request;
        Calculator calculator = new Calculator();
        System.out.println("Welcome! To understand functionalities enter '/help'");

        while (true) {
            try {
                request = scanner.nextLine().trim();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers, operations, commands and assignments only.\nEnter \"/help\" for more information.\n");
                continue;
            }
            if (request.isEmpty()){
                continue;
            } else if (request.matches(String.valueOf(PatternChecker.getAssignmentPattern()))) {
                calculator.assignment(request);
            } else if (calculator.database.isValidVariableName(request)) {
                if (calculator.database.getValue(request)!=null) {
                    System.out.println(calculator.database.getValue(request));
                } else {
                    calculator.database.invalidVariableNameError();
                }
            } else if (request.contains("=")) {
                calculator.database.identifierError();
            } else if (request.startsWith("/")) {
                calculator.command.execute(request);
            } else {
                try {
                    calculator.calculate(request);
                } catch (NoSuchElementException | NullPointerException e) {
                    System.out.println("Invalid expression");
                }
            }
        }
    }
}

