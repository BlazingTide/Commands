package me.blazingtide.commands.agent;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.argument.Argument;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.command.sub.SubCommand;
import me.blazingtide.commands.exception.CommandException;
import me.blazingtide.commands.exception.CommandPermissionException;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.sender.Sender;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
     * @param command   the command object
     * @param label     the label of the command
     */
    void handleException(CommandException exception, Object sender, Command command, String label);

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

        //Split all the arguments & collect them into an array
        String[] arguments = commandString.split(" ");

        //Find the label, the first argument
        final String label = arguments[0];

        //Check if arguments is empty, if it is then set the arguments to be a new empty string
        //if not, exclude the label from the arguments
        if (arguments.length == 1) {
            arguments = new String[]{};
        } else {
            arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        }

        for (Command command : repository) {
            final String[] finalArguments = arguments;

            command.getLabels()
                    .stream()
                    .filter(l -> l.getValue().equalsIgnoreCase(label))
                    .forEach(ignored -> {
                        filterSubCommands(commandString, command, label, sender, finalArguments);
                    });
        }
    }

    default void filterSubCommands(String commandString, Command command, String labelStr, Object sender, String[] arguments) {
        //Find if there's any subcommands
        //If there are sub commands, recursively call this method again with the correct sub commands
        if (arguments.length != 0) {
            String subCommandLabel = arguments[0];

            //A list of all the sub commands registered to the subCommandLabel
            final List<SubCommand> collect = command.getSubCommands()
                    .stream()
                    .filter(sub -> sub.getLabels().stream().anyMatch(l1 -> l1.getValue().equalsIgnoreCase(subCommandLabel)))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()) {
                if (arguments.length == 1) {
                    arguments = new String[]{};
                } else {
                    arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
                }
                final String[] finalArguments = arguments;
                //Gotta do this continuously since we allow unlimited sub commands
                labelStr += " " + subCommandLabel;

                final String finalLabelStr = labelStr;
                collect.forEach(subCommand -> filterSubCommands(commandString, subCommand, finalLabelStr, sender, finalArguments));
                return;
            }
        }

        executeCommand(command, sender, labelStr, commandString, arguments);
    }

    default void sendHelp(Command command, Object senderObject, String labelStr) {
    }

    default void executeCommand(Command command, Object senderObject, String labelStr, String commandString, String[] stringArguments) {
        //Capitalize the label str (for cosmetic purposes)
        final StringBuilder newLabelStr = new StringBuilder();
        final char[] chars = labelStr.toCharArray();

        if (command.hasPermission() && !hasPermission(() -> senderObject, command.getPermission())) {
            handleException(new CommandPermissionException(commandString, command.getPermission()), senderObject, command, labelStr);
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (i == 0) {
                c = Character.toUpperCase(c);
            }

            newLabelStr.append(c);
            if (c == ' ' && i + 1 < chars.length) {
                newLabelStr.append(Character.toUpperCase(chars[i + 1]));
                i++;
            }
        }
        labelStr = newLabelStr.toString();

        final Argument[] arguments = new Argument[stringArguments.length];

        for (int i = 0; i < stringArguments.length; i++) {
            final String str = stringArguments[i];
            final Argument argument = () -> Label.of(str);

            arguments[i] = argument;
        }

        final Sender sender = () -> senderObject;

        if (command.getExecutor() == null) {
            sendHelp(command, senderObject, labelStr);
            return;
        }

        try {
            if (command.isAsync()) {
                String finalLabelStr = labelStr;
                runAsync(() -> {
                    try {
                        command.getExecutor().accept(CommandArguments.of(commandString, arguments, sender));
                    } catch (CommandException exception) {
                        handleException(exception, senderObject, command, finalLabelStr);
                    }
                });
            } else {
                command.getExecutor().accept(CommandArguments.of(commandString, arguments, sender));
            }
        } catch (CommandException exception) {
            handleException(exception, senderObject, command, labelStr);
        }
    }

}
