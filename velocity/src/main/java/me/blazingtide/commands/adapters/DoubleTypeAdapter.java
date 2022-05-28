package me.blazingtide.commands.adapters;

import com.velocitypowered.api.command.CommandSource;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DoubleTypeAdapter implements TypeAdapter<Double> {
    @Override
    public Double process(String label) {
        return Double.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSource commandSender = (CommandSource) sender.getSenderObject();

        commandSender.sendMessage(Component.text(given + " is not a valid number.").color(NamedTextColor.RED));
    }
}
