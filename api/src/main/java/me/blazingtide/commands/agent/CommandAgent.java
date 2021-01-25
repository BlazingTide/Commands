package me.blazingtide.commands.agent;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.argument.Argument;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.exception.CommandException;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.sender.Sender;

import java.util.Arrays;
import java.util.Collection;

/**
 * Command agents are handlers that register the newly created commands.
 *
 * <p>
 * By creating an implementation of the command agent, you can have unlimited ways
 * to register and run commands.
 * </p>
 */
public interface CommandAgent {

    /**
     * Runs the given runnable in another thread determined
     * by the agent.
     *
     * @param runnable runnable that's ran async
     */
    void runAsync(Runnable runnable);

    /**
     * Handles a command exception.
     *
     * @param exception the command exception
     * @param command
     */
    void handleException(CommandException exception, Object sender, Command command);

    /**
     * Checks whether a user has permission to perform
     * the command or not.
     *
     * @param sender     the sender
     * @param permission the permission
     * @return has permission
     */
    boolean hasPermission(Sender sender, String permission);

    /**
     * Called whenever a command is executed.
     * The method will find the correct command and then
     * handle that command.
     *
     * @param commandString full command label
     * @param sender        the sender of the command
     */
    default void onCommand(String commandString, Object sender) {
        final Collection<Command> repository = Commands.getCommandService().getRepository().getCollection();

        String[] arguments = commandString.split(" ");

        final String label = arguments[0];

        if (arguments.length == 1) {
            arguments = new String[]{};
        } else {
            arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        }

        for (Command command : repository) {
            if (label.equalsIgnoreCase(command.getLabel().getValue())) {
                executeCommand(command, sender, commandString, arguments);
                break;
            }
        }
    }

    private void executeCommand(Command command, Object senderObject, String commandString, String[] stringArguments) {
        final Argument[] arguments = new Argument[stringArguments.length];

        for (int i = 0; i < stringArguments.length; i++) {
            final String str = stringArguments[i];
            final Argument argument = () -> Label.of(str);

            arguments[i] = argument;
        }

        final Sender sender = () -> senderObject;

        try {
            if (command.isAsync()) {
                runAsync(() -> command.getExecutor().accept(CommandArguments.of(commandString, arguments, sender)));
            } else {
                command.getExecutor().accept(CommandArguments.of(commandString, arguments, sender));
            }
        } catch (CommandException exception) {
            handleException(exception, senderObject, command);
        }
    }

}
