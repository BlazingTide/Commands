package me.blazingtide.commands.argument.cursor;

import me.blazingtide.commands.argument.ArgumentCursor;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.argument.CursorResult;
import me.blazingtide.commands.label.Label;

public class NonNullArgumentCursor implements ArgumentCursor {

    private final int index;
    private final CommandArguments commandArguments;
    private final Label label;
    private String permission;

    /**
     * Ensures that this object can only be created
     * through the static constructor method.
     *
     * @param index            the index of the argument
     * @param commandArguments the command arguments object
     * @param label            the label for the argument
     */
    protected NonNullArgumentCursor(int index, CommandArguments commandArguments, Label label) {
        this.index = index;
        this.commandArguments = commandArguments;
        this.label = label;
    }

    /**
     * Creates a new NonNullArgumentCursor
     *
     * @return the new cursor object
     */
    public static NonNullArgumentCursor create(int index, CommandArguments arguments, String label) {
        return new NonNullArgumentCursor(index, arguments, Label.of(label));
    }

    public <T> T as(Class<T> clazz) {
        final CursorResult result = CursorResult.of(label, permission);

        return runChecks(index, clazz, commandArguments.getCommandString(), commandArguments.getSender(), commandArguments, result);
    }

    @Override
    public NullableArgumentCursor allowEmpty() {
        return NullableArgumentCursor.create(index, commandArguments, label.getValue()).permission(permission);
    }

    @Override
    public NonNullArgumentCursor permission(String permission) {
        this.permission = permission;

        return this;
    }

    @Override
    public Label getLabel() {
        return label;
    }

}
