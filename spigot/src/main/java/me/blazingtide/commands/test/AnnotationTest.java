package me.blazingtide.commands.test;

import me.blazingtide.commands.annotation.Command;
import org.bukkit.entity.Player;

public class AnnotationTest {

    @Command(labels = {"test stuff t2"})
    public void execute(Player player, String test, String target) {
        player.sendMessage("YOU DID IT! " + test);
    }

}
