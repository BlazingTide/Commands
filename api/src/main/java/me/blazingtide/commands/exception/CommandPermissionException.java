package me.blazingtide.commands.exception;

public class CommandPermissionException extends CommandException {

    private final String permission;

    public CommandPermissionException(String commandString, String permission) {
        super(commandString);
        this.permission = permission;
    }

}
