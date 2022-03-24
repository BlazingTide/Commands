package me.blazingtide.commands.repository;

import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;

import java.util.Collection;
import java.util.Map;

/**
 * A command repository is a object that caches and retrieves command objects
 *
 * @param <E> object type
 * @param <T> collection type
 */
public interface CommandRepository<E extends Command, T extends Map<String, E>> {

    /**
     * Creates a basic implementation of the command repository
     * object.
     *
     * @param <T> type of collection
     * @return the basic command repository
     */
    static <T extends Collection<Command>> CommandRepository<Command, Map<String, Command>> basic() {
        return new CommandRepositoryImpl();
    }

    /**
     * The collection that stores all the commands
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
        for (Label label : object.getLabels()) {
            getCollection().put(label.getValue(), object);
        }
    }

}
