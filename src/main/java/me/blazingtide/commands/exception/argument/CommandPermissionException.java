package me.blazingtide.commands.exception.argument;

public class CommandPermissionException extends CommandArgumentException {

    private final String permission;

    public CommandPermissionException(String commandString, String argument, String permission) {
        super(commandString, argument);
        this.permission = permission;
    }

}
