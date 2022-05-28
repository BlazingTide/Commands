package me.blazingtide.commands.sender;

import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.sender.CommandSenderException;

import java.util.Objects;

/**
 * This class represents the user that sent the command.
 */
public interface Sender {

    /**
     * The object that is the sender.
     *
     * <p>
     * If the sender is a Spigot Player then it will return a Player object
     * or if the sender is a custom sender object then it will return
     * that custom sender object.
     * </p>
     *
     * @return the sender object
     */
    Object getSenderObject();

    /**
     * Returns the sender object casted as the type supplied.
     *
     * @param clazz         the class of the type of sender
     * @param commandString the entire command inputted
     * @param <T>           the type of sender
     * @return the casted sender
     * @throws CommandArgumentCastException thrown if the sender object {@link #getSenderObject()} and the class of type are not the same type
     */
    default <T> T getSender(Class<T> clazz, String commandString) throws CommandArgumentCastException {
        final Object object = getSenderObject();

        Objects.requireNonNull(clazz);
        Objects.requireNonNull(object);

        if (!clazz.isInstance(object)) {
            throw new CommandSenderException(commandString);
        }

        return clazz.cast(object);
    }

    static Sender of(Object object) {
        return () -> object;
    }

}
