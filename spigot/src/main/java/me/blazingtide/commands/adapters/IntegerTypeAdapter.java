package me.blazingtide.commands.adapters;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.sender.Sender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {
    @Override
    public Integer process(Label label) {
        return Integer.valueOf(label.getValue());
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
