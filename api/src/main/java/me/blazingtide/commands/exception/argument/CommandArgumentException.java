package me.blazingtide.commands.exception.argument;

import me.blazingtide.commands.exception.CommandException;

public class CommandArgumentException extends CommandException {

    private final String argument;

    public CommandArgumentException(String commandString, String argument) {
        super(commandString);
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }
}
