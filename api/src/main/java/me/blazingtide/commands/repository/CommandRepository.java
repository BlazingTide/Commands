package me.blazingtide.commands.repository;

import me.blazingtide.commands.command.Command;

import java.util.Collection;
import java.util.List;

/**
 * A command repository is a object that caches and retrieves command objects
 *
 * @param <E> object type
 * @param <T> collection type
 */
public interface CommandRepository<E, T extends Collection<E>> {

    /**
     * Creates a basic implementation of the command repository
     * object.
     *
     * @param <T> type of collection
     * @return the basic command repository
     */
    static <T extends Collection<Command>> CommandRepository<Command, List<Command>> basic() {
        return new CommandRepositoryImpl();
    }

    /**
     * The collection that stores all of the commands
     *
     * @return the collection
     */
    T getCollection();

    /**
     * Adds an object to the collection.
     *
     * @param object object to add
     */
    default void add(E object) {
        getCollection().add(object);
    }

}
