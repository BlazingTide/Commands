package me.blazingtide.commands.exception.argument;

import me.blazingtide.commands.exception.CommandException;

public class CommandArgumentEmptyException extends CommandException {

    public CommandArgumentEmptyException(String commandString) {
        super(commandString);
    }

}
