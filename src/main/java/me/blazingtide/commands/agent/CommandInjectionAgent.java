package me.blazingtide.commands.agent;

import me.blazingtide.commands.command.Command;

public interface CommandInjectionAgent extends CommandAgent {

    void inject(Command command);

}
