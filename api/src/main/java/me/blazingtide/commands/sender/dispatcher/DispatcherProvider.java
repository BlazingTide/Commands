package me.blazingtide.commands.sender.dispatcher;

import me.blazingtide.commands.sender.Sender;

import java.util.Optional;

/**
 * Provides a dispatcher for the sender.
 */
public interface DispatcherProvider<T> {

    Optional<T> provide(Sender sender);

}
