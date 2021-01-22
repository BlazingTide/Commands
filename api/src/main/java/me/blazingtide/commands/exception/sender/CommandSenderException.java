package me.blazingtide.commands.exception.sender;

import me.blazingtide.commands.exception.CommandException;

public class CommandSenderException extends CommandException {

    public CommandSenderException(String commandString) {
        super(commandString);
    }

}
