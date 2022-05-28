package me.blazingtide.commands.model;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.blazingtide.commands.Commands;
import me.blazingtide.commands.CommandsVelocityPlugin;
import me.blazingtide.commands.autocomplete.AutoCompleteProvider;
import me.blazingtide.commands.autocomplete.StringUtil;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.sender.Sender;

import java.util.ArrayList;
import java.util.List;

public class VelocityCommand implements AutoCompleteProvider, SimpleCommand {

    private final Command command;

    public VelocityCommand(Command command) {
        this.command = command;
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        final String[] args = invocation.arguments();
        final CommandSource sender = invocation.source();

        final List<String> autoComplete = processInput(args, Sender.of(sender), command);

        return autoComplete != null ? autoComplete : defaultTabComplete(args);
    }

    /**
     * Code as seen in {@link org.bukkit.command.Command}.
     * Simply using this to give the default tab complete if our tab completer fails
     * to find any tab completion results.
     *
     * @param args the arguments
     * @return tab complete results
     */
    public List<String> defaultTabComplete(String[] args) {
        String lastWord = args[args.length - 1];
        ArrayList<String> matchedPlayers = new ArrayList<>();

        for (Player player : CommandsVelocityPlugin.get().getServer().getAllPlayers()) {
            String name = player.getUsername();

            if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }

        matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }

    @Override
    public void execute(Invocation invocation) {
        final String str = invocation.alias();
        final String[] args = invocation.arguments();
        final CommandSource sender = invocation.source();

        final StringBuilder builder = new StringBuilder();

        builder.append(str).append(" ");

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        Commands.getCommandService().getAgent().onCommand(builder.toString().trim(), sender);
    }
}
