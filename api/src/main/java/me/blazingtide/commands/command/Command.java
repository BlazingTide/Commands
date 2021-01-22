package me.blazingtide.commands.command;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.builder.CommandBuilderImpl;
import me.blazingtide.commands.builder.CommandBuilder;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.permission.PermissionHolder;

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
     * The label of the command
     *
     * @return the label
     */
    Label getLabel();

    /**
     * Returns the usage for the command.
     * The usage is sent to the player if the user doesn't input
     * the correct amount of arguments.
     *
     * @return the command usage
     */
    String getUsage();

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
     * Clones the command and returns a new command builder
     *
     * @return the command builder
     */
    default CommandBuilder clone() {
        final CommandBuilder builder = new CommandBuilderImpl()
                .label(this.getLabel().getValue())
                .usage(this.getUsage())
                .permission(this.getPermission())
                .execute(this.getExecutor());

        if (isAsync()) {
            builder.async();
        }

        return builder;
    }

}
