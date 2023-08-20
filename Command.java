package calculator;

public class Command {

    CalculatorDatabase database;

    String help = "/help";
    String exit = "/exit";
    String data = "/data";

    public void execute(String commandType) {
        if (commandType.equalsIgnoreCase(help)) {
            System.out.print("The program also performs operations of exponents, division, multiplication, addition and subtraction.\n" +
                    "The program supports parenthesis '(' or ')' in operations.\n" +
                    "Operands and operators must include space between them (eg: '8 / 3').\n" +
                    "Sequence of multiple '+' and '-' in operations is valid (eg: 'x +-- 6').\n" +
                    "The program stores variable assigned to integer values (variables can only contain letters).\n" +
                    "Variable value can be accessed by typing variable name (eg:'x', 'var1', 'buzznum').\n" +
                    "Variables can be reassigned (eg:'a=9','a=b','a= buzznum').\n" +
                    "To view store variables enter \"/data\".\n" +
                    "To exit enter \"/exit\".\n");
        } else if (commandType.equalsIgnoreCase(exit)) {
            System.out.println("Bye!");
            System.exit(0);
        } else if (commandType.equalsIgnoreCase(data)) {
            database.getAllVariables();
        } else {
            System.out.println("Unknown command");
        }
    }

    public void setDatabase(CalculatorDatabase database) {
        this.database = database;
    }
}
