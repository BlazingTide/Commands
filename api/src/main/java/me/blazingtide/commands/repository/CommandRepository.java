package me.blazingtide.commands.repository;

import me.blazingtide.commands.command.Command;

import java.util.Collection;
import java.util.List;

public interface CommandRepository<E, T extends Collection<E>> {

    static <T extends Collection<Command>> CommandRepository<Command, List<Command>> basic() {
        return new BasicCommandRepository();
    }

    T getCollection();

}
