package me.blazingtide.commands.exception.argument;

public class CommandArgumentCastException extends CommandArgumentException {

    public CommandArgumentCastException(String commandString, String argument) {
        super(commandString, argument);
    }

}
