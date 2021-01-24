package me.blazingtide.commands.argument;

import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.argument.cursor.NullableArgumentCursor;
import me.blazingtide.commands.cursor.Cursor;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;

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

    default void runChecks(int index, CommandArguments arguments, CursorResult result) {
        if (arguments.getArguments().length <= index && this instanceof NonNullArgumentCursor) {
            throw new CommandArgumentEmptyException(arguments.getCommandString());
        }

        final Argument argument = arguments.getArguments()[index];


    }

}
