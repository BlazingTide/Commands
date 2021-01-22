package me.blazingtide.commands.service;

import me.blazingtide.commands.agent.CommandAgent;
import me.blazingtide.commands.repository.CommandRepository;

public interface CommandServiceBuilder {

    CommandServiceBuilder agent(CommandAgent agent);

    CommandServiceBuilder repository(CommandRepository repository);

    CommandService register();

}
