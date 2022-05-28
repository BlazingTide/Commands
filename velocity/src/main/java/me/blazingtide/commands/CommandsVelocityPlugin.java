package me.blazingtide.commands;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.blazingtide.commands.adapters.*;
import me.blazingtide.commands.agent.VelocityCommandAgent;
import me.blazingtide.commands.repository.CommandRepository;

import java.util.logging.Logger;

public class CommandsVelocityPlugin {

    private static CommandsVelocityPlugin plugin;

    private final ProxyServer server;
    private final Logger logger;
    private final CommandManager manager;

    @Inject
    public CommandsVelocityPlugin(ProxyServer server, Logger logger, CommandManager manager) {
        this.server = server;
        this.logger = logger;
        this.manager = manager;
        plugin = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Commands.newInstance()
                .agent(VelocityCommandAgent.of(this))
                .repository(CommandRepository.basic())
                .register();

        registerDefaults();
    }

    private void registerDefaults() {
        Commands.registerTypeAdapter(Player.class, new PlayerTypeAdapter());
        Commands.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        Commands.registerTypeAdapter(Long.class, new LongTypeAdapter());
        Commands.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
        Commands.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public static CommandsVelocityPlugin get() {
        return plugin;
    }

    public CommandManager getManager() {
        return manager;
    }
}
