package me.blazingtide.commands.exception.argument;

import me.blazingtide.commands.exception.CommandException;

public class CommandArgumentException extends CommandException {

    protected final String argument;

    public CommandArgumentException(String commandString, String argument) {
        super(commandString);
        this.argument = argument;
    }
}
