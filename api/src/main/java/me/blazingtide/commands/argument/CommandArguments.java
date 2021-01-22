package me.blazingtide.commands.argument;

import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;
import me.blazingtide.commands.sender.Sender;

public interface CommandArguments {

    String getCommandString();

    Argument[] getArguments();

    Sender getSender();

    default ArgumentCursor get(int index) throws CommandArgumentEmptyException, CommandArgumentCastException {
        final Argument argument = getArguments().length <= index ? null : getArguments()[index];

        return NonNullArgumentCursor.create(argument == null ? null : argument.getLabel().getValue());
    }

    default <T> T sender(Class<T> clazz) {
        return getSender().getSender(clazz, getCommandString());
    }
}
