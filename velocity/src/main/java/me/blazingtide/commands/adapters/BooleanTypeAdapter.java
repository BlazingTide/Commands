package me.blazingtide.commands.adapters;

import com.velocitypowered.api.command.CommandSource;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public class BooleanTypeAdapter implements TypeAdapter<Boolean> {

    @Override
    public Boolean process(String label) {
        return Boolean.valueOf(label);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSource commandSender = (CommandSource) sender.getSenderObject();

        commandSender.sendMessage(Component.text(given + " is not a valid boolean.").color(NamedTextColor.RED));
    }

    @Override
    public List<String> getAutoComplete(String input, Sender sender) {
        return List.of("true", "false");
    }

}
