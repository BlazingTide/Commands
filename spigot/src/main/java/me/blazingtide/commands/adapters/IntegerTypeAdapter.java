package me.blazingtide.commands.adapters;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {
    @Override
    public Integer process(String label) {
        return Integer.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSender commandSender = (CommandSender) sender.getSenderObject();

        commandSender.sendMessage(ChatColor.RED + given + " is not a valid number.");
    }

    @Override
    public List<Integer> getAutoComplete(String input) {
        return null;
    }
}
