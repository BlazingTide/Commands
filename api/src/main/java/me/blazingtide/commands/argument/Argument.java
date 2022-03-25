package me.blazingtide.commands.argument;

/**
 * This class represents each argument supplied by the user when a command is ran.
 */
public interface Argument {

    /**
     * Determines weather the argument is allowed to be empty
     * or it must have a value.
     *
     * @return allowed argument to be empty
     */
//    boolean allowEmpty();

    /**
     * Returns the String of the argument
     * when the command is supplied.
     *
     * <p>
     * Will return null if the argument is supplied as empty.
     * </p>
     *
     * @return the argument's label
     */
    String getLabel();

}
