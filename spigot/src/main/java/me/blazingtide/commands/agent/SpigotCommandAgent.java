package me.blazingtide.commands.agent;

import me.blazingtide.commands.bukkit.BukkitCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.exception.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Objects;

import static me.blazingtide.commands.CommandsPlugin.SPIGOT_FALLBACK_PREFIX;

public class SpigotCommandAgent implements CommandInjectionAgent {

    protected static Field COMMAND_MAP_FIELD;

    static {
        try {
            COMMAND_MAP_FIELD = Bukkit.getPluginManager().getClass().getField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final JavaPlugin plugin;
    private CommandMap commandMap;

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
    public void handleException(CommandException exception, Object sender) {
        exception.printStackTrace();
    }

    @Override
    public void inject(Command command) {
        registerCommand(command);
    }

    private void registerCommand(Command command) {
        if (commandMap == null) {
            commandMap = getCommandMap();
        }

        final BukkitCommand bukkitCommand = new BukkitCommand(command.getLabel().getValue());

        commandMap.register(SPIGOT_FALLBACK_PREFIX, bukkitCommand);
    }

    private CommandMap getCommandMap() {
        final PluginManager manager = Bukkit.getPluginManager();

        try {
            return (CommandMap) COMMAND_MAP_FIELD.get(manager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
