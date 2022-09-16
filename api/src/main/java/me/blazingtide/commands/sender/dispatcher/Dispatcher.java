package me.blazingtide.commands.sender.dispatcher;

/**
 * Provides a dispatcher for the sender.
 */
public interface Dispatcher {

    static Dispatcher of(Object object) {
        return () -> object;
    }

    /**
     * The raw object of the dispatcher.
     *
     * @return the raw object
     */
    Object value();

}
