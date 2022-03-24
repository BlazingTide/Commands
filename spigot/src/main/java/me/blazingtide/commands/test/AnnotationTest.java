package me.blazingtide.commands.test;

import me.blazingtide.commands.annotation.Command;
import org.bukkit.entity.Player;

public class AnnotationTest {

    @Command(labels = {"test test"})
    public void execute(Player player) {
        player.sendMessage("YOU DID IT!");
    }

}
