package me.blazingtide.commands.sender.dispatcher;

import me.blazingtide.commands.sender.Sender;

/**
 * Provides a dispatcher for the sender.
 */
public interface Dispatcher {

    static Dispatcher of(Object object) {
        return () -> Sender.of(object);
    }

    /**
     * The sender.
     *
     * @return the sender
     */
    Sender value();

}
