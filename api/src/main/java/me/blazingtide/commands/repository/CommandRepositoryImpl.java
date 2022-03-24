package me.blazingtide.commands.repository;

import me.blazingtide.commands.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandRepositoryImpl implements CommandRepository<Command, List<Command>> {

    private final List<Command> commands = new ArrayList<>();

    @Override
    public List<Command> getCollection() {
        return commands;
    }
}
