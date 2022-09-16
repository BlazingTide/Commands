package me.blazingtide.commands.sender.dispatcher;

import java.util.Optional;

/**
 * Provides a dispatcher for the sender.
 */
public interface DispatcherProvider<T> {

    Optional<T> provide(Dispatcher dispatcher);

}
