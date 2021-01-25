package me.blazingtide.commands.exception.argument;

import me.blazingtide.commands.exception.CommandException;

public class CommandArgumentTypeNotFoundException extends CommandArgumentException {

    private final Class<?> type;

    public CommandArgumentTypeNotFoundException(String commandString, String argument, Class<?> type) {
        super(commandString, argument);
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
