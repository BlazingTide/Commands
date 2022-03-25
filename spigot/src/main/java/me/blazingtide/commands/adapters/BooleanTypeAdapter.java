package me.blazingtide.commands.adapters;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BooleanTypeAdapter implements TypeAdapter<Boolean> {
    @Override
    public Boolean process(String label) {
        return Boolean.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSender commandSender = (CommandSender) sender.getSenderObject();

        commandSender.sendMessage(ChatColor.RED + given + " is not a valid boolean.");
    }

    @Override
    public List<String> getAutoComplete(String input) {
        return List.of("true", "false");
    }
}
