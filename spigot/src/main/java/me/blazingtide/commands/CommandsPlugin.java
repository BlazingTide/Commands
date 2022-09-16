package me.blazingtide.commands;

import me.blazingtide.commands.adapters.*;
import me.blazingtide.commands.agent.SpigotCommandAgent;
import me.blazingtide.commands.repository.CommandRepository;
import me.blazingtide.commands.testing.DispatcherTest;
import me.blazingtide.commands.testing.WorldDispatcher;
import me.blazingtide.commands.testing.WorldDispatcherProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandsPlugin extends JavaPlugin {

    public static String SPIGOT_FALLBACK_PREFIX = "commands";

    @Override
    public void onEnable() {
        Commands.newInstance()
                .agent(SpigotCommandAgent.of(this))
                .repository(CommandRepository.basic())
                .register();

        registerDefaults();
        processConfig();
        Commands.registerDispatcher(WorldDispatcher.class, new WorldDispatcherProvider());
        Commands.registerAnnotations(new DispatcherTest());
    }

    private void processConfig() {
        saveDefaultConfig();

        SPIGOT_FALLBACK_PREFIX = getConfig().getString("default_fallback_prefix");
    }

    private void registerDefaults() {
        Commands.registerTypeAdapter(Player.class, new PlayerTypeAdapter());
        Commands.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        Commands.registerTypeAdapter(Long.class, new LongTypeAdapter());
        Commands.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
        Commands.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
    }

}
