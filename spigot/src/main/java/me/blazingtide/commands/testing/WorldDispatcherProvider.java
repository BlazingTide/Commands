package me.blazingtide.commands.testing;

import me.blazingtide.commands.sender.dispatcher.Dispatcher;
import me.blazingtide.commands.sender.dispatcher.DispatcherProvider;
import org.bukkit.entity.Player;

import java.util.Optional;

public class WorldDispatcherProvider implements DispatcherProvider<WorldDispatcher> {

    @Override
    public Optional<WorldDispatcher> provide(Dispatcher dispatcher) {
        var player = dispatcher.value().of(Player.class);

        if (player == null) return Optional.empty();

        return Optional.of(new WorldDispatcher(player, player.getWorld()));
    }
}
