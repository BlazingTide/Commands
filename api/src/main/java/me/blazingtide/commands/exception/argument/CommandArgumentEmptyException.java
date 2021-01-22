package me.blazingtide.commands.exception.argument;

public class CommandArgumentEmptyException extends CommandArgumentException {

    public CommandArgumentEmptyException(String commandString, String argument) {
        super(commandString, argument);
    }

}
