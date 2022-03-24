package me.blazingtide.commands.repository;

import me.blazingtide.commands.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandRepositoryImpl implements CommandRepository<Command, Map<String, Command>> {

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public Map<String, Command> getCollection() {
        return commands;
    }
}
