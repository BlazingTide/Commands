package me.blazingtide.commands.argument;

import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.permission.PermissionHolder;

/**
 * This class represents each argument supplied by the user when a command is ran.
 * <p>
 * The argument class extends {@link PermissionHolder} to store
 * whether the argument requires a permission to be accessed.
 */
public interface Argument extends PermissionHolder {

    /**
     * Determines weather the argument is allowed to be empty
     * or it must have a value.
     *
     * @return allowed argument to be empty
     */
    boolean allowEmpty();

    /**
     * Returns the Label of the argument
     * when the command is supplied.
     *
     * <p>
     * Will return null if the argument is supplied as empty.
     * </p>
     *
     * @return the argument's label
     */
    Label getLabel();

}
