package me.blazingtide.commands.bukkit;

import com.google.common.collect.Lists;
import me.blazingtide.commands.Commands;
import me.blazingtide.commands.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitCommand extends org.bukkit.command.Command {

    private final Command command;

    public BukkitCommand(String name, Command command) {
        super(name);
        this.command = command;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        final String lastWords = args.length == 0 ? "" : args[args.length - 1];
        final String[] arguments = new String[args.length + 1];
        arguments[0] = alias;
        System.arraycopy(args, 0, arguments, 1, args.length);

        final Command command = args.length <= 1 ? this.command : traverse(arguments, 0, this.command);

        if (command != null) {
            final List<String> subCommands = Lists.newArrayList();

            final List<List<String>> labels = command.getSubCommands()
                    .stream()
                    .map(Command::getLabels)
                    .collect(Collectors.toList());

            labels.forEach(strings -> subCommands.addAll(strings.stream().filter(str -> StringUtil.startsWithIgnoreCase(str, lastWords)).collect(Collectors.toList())));

            if (!subCommands.isEmpty()) {
                subCommands.sort(String.CASE_INSENSITIVE_ORDER);

                return subCommands;
            }
        }

        return defaultTabComplete(sender, args);
    }

    private static Command traverse(String[] label, int index, Command parent) {
        final String argument = label[index].trim();

        if (index == label.length - 1 || argument.isBlank()) {
            return parent; //Final parent
        }

        for (Command subCommand : parent.getSubCommands()) {
            if (subCommand.getLabels().contains(argument)) {
                return traverse(label, index + 1, subCommand);
            }
        }

        return parent;
    }

    /**
     * Code as seen in {@link org.bukkit.command.Command}.
     * Simply using this to give the default tab complete if our tab completer fails
     * to find any tab completion results.
     *
     * @param sender the command sender
     * @param args   the arguments
     * @return tab complete results
     */
    public List<String> defaultTabComplete(CommandSender sender, String[] args) {
        String lastWord = args[args.length - 1];

        Player senderPlayer = sender instanceof Player ? (Player) sender : null;

        ArrayList<String> matchedPlayers = new ArrayList<>();
        for (Player player : sender.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }

        matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
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
