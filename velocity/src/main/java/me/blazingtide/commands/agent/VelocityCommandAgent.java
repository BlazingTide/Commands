package me.blazingtide.commands.agent;

import com.velocitypowered.api.command.CommandSource;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.exception.CommandException;
import me.blazingtide.commands.exception.CommandPermissionException;
import me.blazingtide.commands.exception.argument.CommandArgumentCastException;
import me.blazingtide.commands.exception.argument.CommandArgumentEmptyException;
import me.blazingtide.commands.exception.argument.CommandArgumentTypeNotFoundException;
import me.blazingtide.commands.exception.sender.CommandSenderException;
import me.blazingtide.commands.sender.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import me.blazingtide.commands.CommandsVelocityPlugin;
import me.blazingtide.commands.model.VelocityCommand;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class VelocityCommandAgent implements CommandInjectionAgent {

    private final CommandsVelocityPlugin plugin;

    //Handle async tasks
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);


    protected VelocityCommandAgent(CommandsVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public static VelocityCommandAgent of(CommandsVelocityPlugin plugin) {
        Objects.requireNonNull(plugin);
        return new VelocityCommandAgent(plugin);
    }

    @Override
    public void runAsync(Runnable runnable) {
        executorService.submit(runnable);
    }

    @Override
    public void handleException(CommandException exception, Object senderObject, Command command, String label) {
        final CommandSource sender = (CommandSource) senderObject;

        if (exception instanceof CommandSenderException) {
            sender.sendMessage(Component.text("You cannot run this command!").color(NamedTextColor.RED));
        }

        if (exception instanceof CommandArgumentEmptyException) {
            sender.sendMessage(Component.text("Not enough arguments: /" + label.toLowerCase(Locale.ROOT) + " " + command.getUsage()).color(NamedTextColor.RED));
        }

        if (exception instanceof CommandArgumentTypeNotFoundException) {
            sender.sendMessage(Component.text("Command type argument is not correctly set, please refer to an administrator.").color(NamedTextColor.RED));
            exception.printStackTrace();
        }

        if (exception instanceof CommandPermissionException) {
            sender.sendMessage(Component.text("I'm sorry, but you do not have permission to perform this command.").color(NamedTextColor.RED));
        }

        if (exception instanceof CommandArgumentCastException) {

        }
    }

    @Override
    public void sendHelp(Command command, Object senderObject, String label) {
        final CommandSource sender = (CommandSource) senderObject;

        final TextColor PRIMARY_COLOR = TextColor.fromHexString("#59afff");
        final TextColor SECONDARY_COLOR = TextColor.fromHexString("#bddfff");

        sender.sendMessage(Component.text());
        sender.sendMessage(Component.text(label + " Help:").color(PRIMARY_COLOR).decorate(TextDecoration.BOLD));
        for (Command subCommand : command.getSubCommands()) {
            final String arg = String.join(",", subCommand.getLabels());

            String usage = "";

            if (subCommand.getUsage() != null && !subCommand.getUsage().isEmpty()) {
                usage = " " + subCommand.getUsage();
            }

            sender.sendMessage(Component.text(" - /" + label.toLowerCase(Locale.ROOT) + " " + arg + usage + "  - " + subCommand.getDescription()).color(SECONDARY_COLOR));
        }
        if (command.getSubCommands().isEmpty()) {
            sender.sendMessage(Component.text(" No sub commands registered").color(SECONDARY_COLOR));
        }
        sender.sendMessage(Component.text());
    }

    @Override
    public boolean hasPermission(Sender sender, String permission) {
        final Object spigotSender = sender.getSenderObject();

        return spigotSender instanceof CommandSource && ((CommandSource) spigotSender).hasPermission(permission);
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
        final String primaryLabel = command.getLabels().get(0);

        if (command.getLabels().size() >= 1) {
            final String[] alias = new String[command.getLabels().size() - 1];

            for (int i = 1; i < command.getLabels().size(); i++) {
                alias[i - 1] = command.getLabels().get(i);
            }

            CommandsVelocityPlugin.get().getManager().register(primaryLabel, new VelocityCommand(command), alias);
            return;
        }

        CommandsVelocityPlugin.get().getManager().register(primaryLabel, new VelocityCommand(command));
    }
}
