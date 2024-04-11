package brokeculator.enumerators;

public enum CommandErrorMessages {
    INVALID_EXIT_COMMAND("Invalid command. Please enter 'exit' to exit the program."),
    INVALID_LIST_COMMAND("Invalid input format for list command. Please use the format:" + System.lineSeparator()
            + "\tlist /a [optional] <amount to list>"),
    INVALID_DELETE_COMMAND("Invalid input format for delete command. Please use the format:" + System.lineSeparator()
            + "\tdelete /i <index>"),
    INVALID_ADD_COMMAND("Invalid input format for add command. Please use the format:" + System.lineSeparator()
            + "\tadd /n <description> /d <date> /a <amount> /c [optional] <category>");

    private String string;
    private CommandErrorMessages(String string) {
        this.string = string;
    }
    public String getString() {
        return string;
    }
}
