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

    private static Command traverse(String[] arguments, int index, Command parent) {
        //faction test <player>
        //faction <empty argument>


        //Don't worry about 0, it'll never be 0
        if (arguments.length == 0) {
            return null;
        }

        /*  binary tree model, makes it easier to understand this algorithm

                            [root: 0, {''}]
                        |           |
                [sub command: 1, {'stuff', ''}]   [sub command]
                    |
               [sub command: 2, {'stuff', 't1', ''}]
         */

        //Root
        if (arguments.length == 1) {
            return parent; //0: false
        }

        if (index + 1 == arguments.length) {
            return parent; //0: false
        }

        if (parent.getSubCommands().isEmpty()) {
            return parent; //0: false
        }

        for (Command subCommand : parent.getSubCommands()) {
            if (subCommand.getLabels().stream().anyMatch(label -> label.equalsIgnoreCase(arguments[index]))) {
                return traverse(arguments, index + 1, subCommand); //0: stuff
            }
        }

        //test stuff t2

        /*
                Recursion Trace: arguments: [stuff, t2, '']

                0: nothing
         */

        return parent;
    }

    private void filterSubCommands(String[] arguments, Command command) {
        //Find if there's any subcommands
        //If there are sub commands, recursively call this method again with the correct sub commands
        if (arguments.length != 0) {
            String subCommandLabel = arguments[0];

            //A list of all the sub commands registered to the subCommandLabel
            final List<Command> collect = command.getSubCommands()
                    .stream()
                    .filter(sub -> sub.getLabels().stream().anyMatch(l1 -> l1.equalsIgnoreCase(subCommandLabel)))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()) {
                if (arguments.length == 1) {
                    arguments = new String[]{};
                } else {
                    arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
                }
                final String[] finalArguments = arguments;

                collect.forEach(subCommand -> filterSubCommands(finalArguments, subCommand));
                return;
            }
        }

        //At this point, we've successfully found the command
        System.out.println("Found command: " + command.getLabels());
    }

    public List<List<String>> getLinkedAutoCompletes(List<Command> commands) {
        final List<List<String>> linkedAutoCompletes = Lists.newArrayList();

        commands.forEach(cmd -> linkedAutoCompletes.add(cmd.getLabels()));

        return linkedAutoCompletes;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        final String lastWords = args.length == 0 ? "" : args[args.length - 1];

        final Command traverse = traverse(args, 0, command);

        System.out.println("Possibly: " + (traverse != null ? traverse.getLabels() : "null") + ", " + Arrays.toString(args));

//        final Map.Entry<List<Command>, Integer> traverse = traverse(args, command);
//        final List<List<String>> autoCompletes = getLinkedAutoCompletes(traverse.getKey());
//        final List<String> autoComplete = autoCompletes.get(args.length - 1);
//
//
//
//        if (autoComplete != null) {
//            final List<String> toReturn = autoComplete.stream().filter(str -> StringUtil.startsWithIgnoreCase(str, lastWords)).collect(Collectors.toList());
//
//            if (!toReturn.isEmpty()) {
//                toReturn.sort(String.CASE_INSENSITIVE_ORDER);
//
//                return toReturn;
//            }
//
//        }

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
