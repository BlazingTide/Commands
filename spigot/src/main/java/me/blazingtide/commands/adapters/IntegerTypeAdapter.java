package me.blazingtide.commands.adapters;

import com.google.common.collect.Lists;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {
    @Override
    public Integer process(String label) {
        return Integer.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        sender.of(CommandSender.class).sendMessage(ChatColor.RED + given + " is not a valid number.");
    }

    @Override
    public List<String> getAutoComplete(String input, Sender sender) {
        final ArrayList<String> toReturn = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            toReturn.add(String.valueOf(i));
        }

        return toReturn;
    }
}
