package me.blazingtide.commands.argument.cursor;

import me.blazingtide.commands.argument.ArgumentCursor;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.argument.CursorResult;
import me.blazingtide.commands.label.Label;

import java.util.Optional;

public class NullableArgumentCursor implements ArgumentCursor {

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
    protected NullableArgumentCursor(int index, CommandArguments commandArguments, Label label) {
        this.index = index;
        this.commandArguments = commandArguments;
        this.label = label;
    }

    /**
     * Creates a new NonNullArgumentCursor
     *
     * @return the new cursor object
     */
    public static NullableArgumentCursor create(int index, CommandArguments arguments, String label) {
        return new NullableArgumentCursor(index, arguments, Label.of(label));
    }

    public <T> Optional<T> as(Class<T> clazz) {
        final CursorResult result = CursorResult.of(label, permission);
        final T mapped = runChecks(index, clazz, commandArguments.getCommandString(), commandArguments.getSender(), commandArguments, result);

        return mapped == null ? Optional.empty() : Optional.of(mapped);
    }

    @Override
    public NullableArgumentCursor allowEmpty() {
        return null;
    }

    @Override
    public NullableArgumentCursor permission(String permission) {
        this.permission = permission;

        return this;
    }

    @Override
    public Label getLabel() {
        return label;
    }

}
