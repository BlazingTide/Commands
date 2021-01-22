package me.blazingtide.commands.argument.cursor;

import me.blazingtide.commands.argument.ArgumentCursor;
import me.blazingtide.commands.label.Label;

import java.util.Optional;

public class NullableArgumentCursor implements ArgumentCursor {

    private final Label label;
    private String permission;

    /**
     * Ensures that this object can only be created
     * through the static constructor method.
     *
     * @param label the label for the argument
     */
    protected NullableArgumentCursor(Label label) {
        this.label = label;
    }

    /**
     * Creates a new NullableArgumentCursor
     *
     * @return the new cursor object
     */
    public static NullableArgumentCursor create(String label) {
        return new NullableArgumentCursor(Label.of(label));
    }

    @Override
    public NullableArgumentCursor allowEmpty() {
        return this;
    }

    @Override
    public NullableArgumentCursor permission(String permission) {
        this.permission = permission;

        return this;
    }

    public <T> Optional<T> as(Class<T> clazz) {
        return Optional.empty();
    }
}
