package me.blazingtide.commands.exception;

public class CommandException extends RuntimeException {

    private final String commandString;

    public CommandException(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }
}
