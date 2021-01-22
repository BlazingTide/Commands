package me.blazingtide.commands;

import me.blazingtide.commands.agent.SpigotCommandAgent;
import me.blazingtide.commands.repository.CommandRepository;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Commands.newInstance()
                .agent(SpigotCommandAgent.of(this))
                .repository(CommandRepository.basic())
                .register();
    }
}
