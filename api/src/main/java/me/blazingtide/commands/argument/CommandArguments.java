package me.blazingtide.commands.argument;

import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;
import me.blazingtide.commands.sender.Sender;

public interface CommandArguments {

    static CommandArguments of(String commandString, Argument[] arguments, Sender sender) {
        return new CommandArguments() {
            @Override
            public String getCommandString() {
                return commandString;
            }

            @Override
            public Argument[] getArguments() {
                return arguments;
            }

            @Override
            public Sender getSender() {
                return sender;
            }
        };
    }

    String getCommandString();

    Argument[] getArguments();

    Sender getSender();

    default NonNullArgumentCursor get(int index) throws CommandArgumentEmptyException, CommandArgumentCastException {
        final Argument argument = getArguments().length <= index ? null : getArguments()[index];

        return NonNullArgumentCursor.create(index, this, argument == null ? null : argument.getLabel());
    }

    default <T> T sender(Class<T> clazz) {
        return getSender().getSender(clazz, getCommandString());
    }

}
