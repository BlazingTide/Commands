package me.blazingtide.commands.adapters;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import me.blazingtide.commands.CommandsVelocityPlugin;

public class PlayerTypeAdapter implements TypeAdapter<Player> {

    @Override
    public Player process(String label) {
        return CommandsVelocityPlugin.get().getServer().getPlayer(label).orElse(null);
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {
        CommandSource commandSender = (CommandSource) sender.getSenderObject();

        commandSender.sendMessage(Component.text(given + " is offline or does not exist.").color(NamedTextColor.RED));
    }
}
