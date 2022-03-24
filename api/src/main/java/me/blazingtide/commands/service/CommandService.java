package me.blazingtide.commands.service;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.agent.CommandAgent;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.repository.CommandRepository;

import java.util.Collection;
import java.util.Map;

/**
 * A service represents a part of the command library that has to be modified
 * depended on usage-case.
 */
public interface CommandService {

    CommandAgent getAgent();

    <T extends Collection<Command>> CommandRepository<Command, Map<String, Command>> getRepository();

    Map<Class<?>, TypeAdapter<?>> getTypeAdapterMap();

}
