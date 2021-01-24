package me.blazingtide.commands.bukkit;

import me.blazingtide.commands.Commands;
import org.bukkit.command.CommandSender;

public class BukkitCommand extends org.bukkit.command.Command {

    public BukkitCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        final StringBuilder builder = new StringBuilder();

        builder.append(str).append(" ");

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        Commands.getCommandService().getAgent().onCommand(builder.toString().trim(), sender);
        return false;
    }
}
