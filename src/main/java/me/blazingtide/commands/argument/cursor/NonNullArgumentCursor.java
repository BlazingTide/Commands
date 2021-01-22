package me.blazingtide.commands.argument.cursor;

import me.blazingtide.commands.argument.ArgumentCursor;
import me.blazingtide.commands.label.Label;

public class NonNullArgumentCursor implements ArgumentCursor {

    private final Label label;
    private String permission;

    /**
     * Ensures that this object can only be created
     * through the static constructor method.
     *
     * @param label the label for the argument
     */
    protected NonNullArgumentCursor(Label label) {
        this.label = label;
    }

    /**
     * Creates a new NonNullArgumentCursor
     *
     * @return the new cursor object
     */
    public static NonNullArgumentCursor create(String label) {
        return new NonNullArgumentCursor(Label.of(label));
    }

    public <T> T as(Class<T> clazz) {
        return null;
    }

    @Override
    public NullableArgumentCursor allowEmpty() {
        return NullableArgumentCursor.create(label.getValue()).permission(permission);
    }

    @Override
    public ArgumentCursor permission(String permission) {
        this.permission = permission;

        return this;
    }

}
