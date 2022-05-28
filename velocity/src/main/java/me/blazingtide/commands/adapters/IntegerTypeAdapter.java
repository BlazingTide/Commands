package me.blazingtide.commands.adapters;

import com.google.common.collect.Lists;
import com.velocitypowered.api.command.CommandSource;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {
    @Override
    public Integer process(String label) {
        return Integer.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSource commandSender = (CommandSource) sender.getSenderObject();

        commandSender.sendMessage(Component.text(given + " is not a valid number.").color(NamedTextColor.RED));
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
