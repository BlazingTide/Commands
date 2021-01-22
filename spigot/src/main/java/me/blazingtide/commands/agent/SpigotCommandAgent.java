package me.blazingtide.commands.agent;

import me.blazingtide.commands.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SpigotCommandAgent implements CommandInjectionAgent {

    private final JavaPlugin plugin;

    protected SpigotCommandAgent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static SpigotCommandAgent of(JavaPlugin plugin) {
        Objects.requireNonNull(plugin);
        return new SpigotCommandAgent(plugin);
    }

    @Override
    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    @Override
    public void inject(Command command) {

    }

}
