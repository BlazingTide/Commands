package me.blazingtide.commands.command.sub.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.sub.SubCommand;
import me.blazingtide.commands.cursor.Cursor;

import java.util.function.Consumer;

/**
 * This class represents the builder responsible for creating
 * commands.
 */
public interface SubCommandBuilder extends Cursor {

    /**
     * Sets the label for the command.
     *
     * @param label the command label
     * @return the command builder
     */
    SubCommandBuilder label(String label);

    /**
     * Sets the usage for the command.
     * The usage is sent to the player if the user doesn't input
     * the correct amount of arguments.
     *
     * @param usage the command usage
     * @return the command builder
     */
    SubCommandBuilder usage(String usage);

    /**
     * Sets the executor for the command.
     * The executor is a Consumer that consumers the type {@link CommandArguments}
     *
     * @param executor the command executor
     * @return the command builder
     */
    SubCommandBuilder execute(Consumer<CommandArguments> executor);

    /**
     * Sets the permission for the command.
     *
     * @param permission the permission
     * @return the command builder
     */
    SubCommandBuilder permission(String permission);

    /**
     * Registers a new sub command to this command.
     * You can have multiple sub commands
     *
     * @param subCommand the sub command
     * @return the command builder
     */
    SubCommandBuilder subCommand(SubCommand subCommand);

    /**
     * Runs the executor provided in {@link #execute(Consumer)} asynchronously
     *
     * @return the command builder
     */
    SubCommandBuilder async();

    /**
     * Creates the sub command object.
     *
     * @return command
     */
    SubCommand create();

}
