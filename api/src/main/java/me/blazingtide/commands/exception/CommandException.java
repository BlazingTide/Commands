package me.blazingtide.commands.exception;

public class CommandException extends RuntimeException {

    protected final String commandString;

    public CommandException(String commandString) {
        this.commandString = commandString;
    }
}
