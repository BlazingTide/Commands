package me.blazingtide.commands.service;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.agent.CommandAgent;
import me.blazingtide.commands.repository.CommandRepository;

import java.util.Objects;

public class CommandServiceBuilderImpl implements CommandServiceBuilder {

    private CommandAgent agent;
    private CommandRepository repository;

    @Override
    public CommandServiceBuilderImpl agent(CommandAgent agent) {
        Objects.requireNonNull(agent);
        this.agent = agent;
        return this;
    }

    @Override
    public CommandServiceBuilderImpl repository(CommandRepository repository) {
        Objects.requireNonNull(repository);
        this.repository = repository;
        return this;
    }

    @Override
    public CommandService register() {
        final CommandService service = new CommandService() {
            @Override
            public CommandAgent getAgent() {
                return agent;
            }

            @Override
            public CommandRepository getRepository() {
                return repository;
            }
        };

        try {
            Commands.setCommandService(service);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return service;
    }
}
