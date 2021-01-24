package me.blazingtide.commands.service;

import me.blazingtide.commands.agent.CommandAgent;
import me.blazingtide.commands.repository.CommandRepository;

/**
 * A service represents a part of the command library that has to be modified
 * depended on usage-case.
 */
public interface CommandService {

    CommandAgent getAgent();

    CommandRepository getRepository();

}
