package me.blazingtide.commands.bukkit;

import com.google.common.collect.Lists;
import me.blazingtide.commands.Commands;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.utils.Pair;
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

    private static Pair<Command, Integer> traverse(String[] arguments, int index, Command parent) {
        //Don't worry about 0, it'll never be 0
        if (arguments.length == 0) {
            return null;
        }

        /*  binary tree model, makes it easier to visualize & test

                            [root: 0, {''}]
                        |           |
                [sub command: 1, {'stuff', ''}]   [sub command]
                    |
               [sub command: 2, {'stuff', 't1', ''}]
         */

        //Root
        if (arguments.length == 1) {
            return new Pair(parent, index);
        }

        if (index + 1 == arguments.length) {
            return new Pair(parent, index);
        }

        if (parent.getSubCommands().isEmpty()) {
            return new Pair(parent, index);
        }

        for (Command subCommand : parent.getSubCommands()) {
            if (subCommand.getLabels().stream().anyMatch(label -> label.equalsIgnoreCase(arguments[index]))) {
                return traverse(arguments, index + 1, subCommand); //0: stuff
            }
        }

        return new Pair(parent, index);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        final String lastWords = args.length == 0 ? "" : args[args.length - 1];

        final Pair<Command, Integer> traverse = traverse(args, 0, command);
        final Command command = traverse.first();
        final Integer depth = traverse.second();

        final List<String> autoComplete = Lists.newArrayList();

        if (command == null) {
            return Lists.newArrayList("error", "unknown", "tab complete");
        }

        if (command.getSubCommands().isEmpty()) {

        }

        if (!command.getSubCommands().isEmpty()) {
            command.getSubCommands().forEach(sub -> autoComplete.addAll(sub.getLabels()));
        }

        final List<String> toReturn = autoComplete.stream().filter(str -> StringUtil.startsWithIgnoreCase(str, lastWords)).collect(Collectors.toList());

        if (!toReturn.isEmpty()) {
            toReturn.sort(String.CASE_INSENSITIVE_ORDER);

            return toReturn;
        }

        return defaultTabComplete(sender, args);
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
