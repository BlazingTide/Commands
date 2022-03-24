package me.blazingtide.commands.bukkit;

import com.google.common.collect.Lists;
import me.blazingtide.commands.Commands;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BukkitCommand extends org.bukkit.command.Command {

    private final Command command;

    public BukkitCommand(String name, Command command) {
        super(name);
        this.command = command;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (!command.getSubCommands().isEmpty() && args.length <= 1) {
            Optional<String> lastWords = args.length == 0 ? Optional.empty() : Optional.of(args[0]);

            final List<String> subCommands = Lists.newArrayList();

            for (Command subCommand : command.getSubCommands()) {
                subCommands.addAll(subCommand.getLabels()
                        .stream()
                        .map(Label::getValue)
                        .filter(str -> !lastWords.isPresent() || StringUtil.startsWithIgnoreCase(str, lastWords.get()))
                        .collect(Collectors.toList()));
            }

            subCommands.sort(String.CASE_INSENSITIVE_ORDER);

            return subCommands;
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
