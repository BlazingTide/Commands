package me.blazingtide.commands.sender;

import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.sender.CommandSenderException;

import java.util.Objects;

public interface Sender {

    Object getSenderObject();

    default <T> T getSender(Class<T> clazz, String commandString) throws CommandArgumentCastException {
        final Object object = getSenderObject();

        Objects.requireNonNull(clazz);
        Objects.requireNonNull(object);

        if (!object.getClass().equals(clazz)) {
            throw new CommandSenderException(commandString);
        }

        return clazz.cast(object.getClass());
    }

}
