package me.blazingtide.commands.argument;

import me.blazingtide.commands.argument.cursor.NullableArgumentCursor;
import me.blazingtide.commands.cursor.Cursor;

/**
 * The class is responsible for finding the argument
 * given and mapping that argument into a type that the user wants.
 */
public interface ArgumentCursor extends Cursor {

    /**
     * Changes the cursor object to a {@link me.blazingtide.commands.argument.cursor.NullableArgumentCursor}
     * so the returned object is a optional instead of a object.
     *
     * @return a new nullable argument cursor
     */
    NullableArgumentCursor allowEmpty();

    /**
     * Sets the permission for the argument.
     *
     * @param permission the permission
     * @return the cursor
     */
    ArgumentCursor permission(String permission);

}
