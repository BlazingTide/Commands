package me.blazingtide.commands.argument;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.argument.cursor.NullableArgumentCursor;
import me.blazingtide.commands.cursor.Cursor;
import me.blazingtide.commands.exception.CommandPermissionException;
import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;
import me.blazingtide.commands.exception.argument.CommandArgumentTypeNotFoundException;
import me.blazingtide.commands.sender.Sender;

/**
 * The class is responsible for finding the argument
 * given and mapping that argument into a type that the user wants.
 */
public interface ArgumentCursor extends Cursor {

    /**
     * Changes the cursor object to a {@link NullableArgumentCursor}
     * so the returned object is a optional instead of a object.
     *
     * @return a new nullable argument cursor
     */
    NullableArgumentCursor allowEmpty();

    /**
     * Sets the permission for the argument.
     * Checks that if the permission is supplied and the argument is not empty
     * that the user has permission to request the argument.
     *
     * @param permission the permission
     * @return the cursor
     */
    ArgumentCursor permission(String permission);

    String getLabel();

    /**
     * Checks whether an argument is valid or not
     * <p>
     * Processes if argument is empty and if a sender has permission to perform the command
     * with the argument present.
     * <p>
     * Will also map the object or return a null if the argument is empty
     *
     * @param <T>           the type the resulting object should be mapped to
     * @param index         the index of the argument
     * @param commandString the full command string
     * @param sender        the sender of the command
     * @param arguments     the command arguments object {@link me.blazingtide.commands.argument.CommandArguments}
     * @param result        the cursor result
     * @return the mapped argument
     */
    default <T> T runChecks(int index, Class<T> clazz, String commandString, Sender sender, CommandArguments arguments, CursorResult result) {
        //Check if argument is present
        if (arguments.getArguments().length <= index && this instanceof NonNullArgumentCursor) {
            throw new CommandArgumentEmptyException(arguments.getCommandString());
        }

        //Check if sender has permission to perform the command with this argument present
        if (result.hasPermission() && !Commands.getCommandService().getAgent().hasPermission(sender, result.getPermission())) {
            throw new CommandPermissionException(commandString, result.getPermission());
        }

        //If the argument is empty and the argument can be empty then return null
        if (this instanceof NullableArgumentCursor && arguments.getArguments().length <= index) {
            return null;
        }

        final Argument argument = arguments.getArguments()[index];
        final String label = argument.getLabel();
        final String labelString = label;

        if (!Commands.getCommandService().getTypeAdapterMap().containsKey(clazz)) {
            throw new CommandArgumentTypeNotFoundException(commandString, labelString, clazz);
        }

        final TypeAdapter<?> typeAdapter = Commands.getCommandService().getTypeAdapterMap().get(clazz);

        try {
            return clazz.cast(transformArgument(label, typeAdapter));
        } catch (Exception e) {
            typeAdapter.onException(sender, labelString, e);

            throw new CommandArgumentCastException(commandString, labelString);
        }
    }

    default Object transformArgument(String label, TypeAdapter<?> adapter) {
        final Object object = adapter.process(label);

        if (object == null) {
            throw new NullPointerException();
        }

        return object;
    }

}
