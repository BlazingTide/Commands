package me.blazingtide.commands.agent;

import me.blazingtide.commands.bukkit.BukkitCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.exception.CommandException;
import me.blazingtide.commands.exception.CommandPermissionException;
import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;
import me.blazingtide.commands.exception.argument.CommandArgumentTypeNotFoundException;
import me.blazingtide.commands.exception.sender.CommandSenderException;
import me.blazingtide.commands.sender.Sender;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import static me.blazingtide.commands.CommandsPlugin.SPIGOT_FALLBACK_PREFIX;

public class SpigotCommandAgent implements CommandInjectionAgent {

    protected static Field COMMAND_MAP_FIELD;

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
    public void handleException(CommandException exception, Object senderObject, Command command, String label) {
        final CommandSender sender = (CommandSender) senderObject;

        if (exception instanceof CommandSenderException) {
            sender.sendMessage(ChatColor.RED + "You cannot run this command!");
        }

        if (exception instanceof CommandArgumentEmptyException) {
            sender.sendMessage(ChatColor.RED + "Not enough arguments: /" + label.toLowerCase(Locale.ROOT) + " " + command.getUsage());
        }

        if (exception instanceof CommandArgumentTypeNotFoundException) {
            sender.sendMessage(ChatColor.RED + "Command type argument is not correctly set, please refer to an administrator.");
            exception.printStackTrace();
        }

        if (exception instanceof CommandPermissionException) {
            sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command.");
        }

        if (exception instanceof CommandArgumentCastException) {

        }
    }

    @Override
    public void sendHelp(Command command, Object senderObject, String label) {
        final CommandSender sender = (CommandSender) senderObject;

        final ChatColor PRIMARY_COLOR = ChatColor.of("#099e0c");
        final ChatColor SECONDARY_COLOR = ChatColor.of("#4ac24c");

        sender.sendMessage(PRIMARY_COLOR.toString() + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "-----------------------------");
        sender.sendMessage(PRIMARY_COLOR + WordUtils.capitalize(label) + " Help");
        sender.sendMessage(ChatColor.WHITE + "All the subcommands of the command " + label + " are listed below.");
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GRAY + " - " + SECONDARY_COLOR + "/" + label + ChatColor.GRAY + ": Displays all subcommands");
        for (Command subCommand : command.getSubCommands()) {
            final String arg = String.join(",", subCommand.getLabels());

            String usage = "";

            if (subCommand.getUsage() != null && !subCommand.getUsage().isEmpty()) {
                usage = " " + subCommand.getUsage();
            }

            sender.sendMessage(ChatColor.GRAY + " - " + SECONDARY_COLOR + "/" + label.toLowerCase(Locale.ROOT) + " " + arg + usage
                    + ChatColor.GRAY + ": " + subCommand.getDescription());
        }
        if (command.getSubCommands().isEmpty()) {
            sender.sendMessage(ChatColor.WHITE + "Failed to find any subcommands");
        }
        sender.sendMessage(PRIMARY_COLOR.toString() + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "-----------------------------");
    }

    @Override
    public boolean hasPermission(Sender sender, String permission) {
        final Object spigotSender = sender.getSenderObject();

        return spigotSender instanceof CommandSender && ((CommandSender) spigotSender).hasPermission(permission);
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public void inject(Command command) {
        registerCommand(command);
    }

    private void registerCommand(Command command) {
        if (commandMap == null) {
            commandMap = getCommandMap();
        }

        for (String label : command.getLabels()) {
            final BukkitCommand bukkitCommand = new BukkitCommand(label, command);

            if (commandMap.getKnownCommands().containsKey(label)) {
                commandMap.getKnownCommands().get(label).unregister(commandMap);
            }

            commandMap.register(SPIGOT_FALLBACK_PREFIX, bukkitCommand);
        }
    }

    private CommandMap getCommandMap() {
        final PluginManager manager = Bukkit.getPluginManager();

        try {
            if (COMMAND_MAP_FIELD == null) {
                COMMAND_MAP_FIELD = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                COMMAND_MAP_FIELD.setAccessible(true);
            }

            return (CommandMap) COMMAND_MAP_FIELD.get(manager);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

}
