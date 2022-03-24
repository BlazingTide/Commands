package me.blazingtide.commands.command;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.builder.CommandBuilder;
import me.blazingtide.commands.command.builder.CommandBuilderImpl;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.permission.PermissionHolder;

import java.util.List;
import java.util.function.Consumer;

/**
 * The command object
 *
 * <p>
 * All commands are immutable. They cannot be changed after the command is created
 * and registered.
 * </p>
 *
 * <p>
 * Commands cannot have multiple labels however, to create a command
 * with the same executor but different labels, you first create a command
 * object and then clone {@link #clone()} the object and set a new label.
 * </p>
 */
public interface Command extends PermissionHolder {

    /**
     * The labels of the command
     *
     * @return the labels
     */
    List<Label> getLabels();

    /**
     * Returns the usage for the command.
     * The usage is sent to the player if the user doesn't input
     * the correct amount of arguments.
     *
     * @return the command usage
     */
    String getUsage();

    /**
     * Returns the description of the command.
     * Use this to label your command and
     * give a valid description for your users.
     *
     * @return the command description
     */
    String getDescription();

    /**
     * Returns the executor for the command.
     * The executor is a Consumer that consumers the type {@link CommandArguments}
     *
     * @return the command executor
     */
    Consumer<CommandArguments> getExecutor();

    /**
     * Runs the executor provided in {@link #getExecutor()} asynchronously
     *
     * @return if the command is async
     */
    boolean isAsync();

    /**
     * A list of the subcommands this command contains.
     *
     * @return map of subcommand
     */
    List<Command> getSubCommands();

    /**
     * Clones the command and returns a new command builder
     *
     * @return the command builder
     */
    default CommandBuilder cloneCommand() {
        final CommandBuilder builder = new CommandBuilderImpl()
                .usage(this.getUsage())
                .permission(this.getPermission())
                .execute(this.getExecutor());

        this.getLabels().forEach(label -> builder.label(label.getValue()));

        if (isAsync()) {
            builder.async();
        }

        return builder;
    }

}
