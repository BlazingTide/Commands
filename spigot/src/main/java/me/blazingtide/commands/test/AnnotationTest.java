package me.blazingtide.commands.test;

import me.blazingtide.commands.annotation.Command;
import me.blazingtide.commands.annotation.OptionalParam;
import org.bukkit.entity.Player;

public class AnnotationTest {

    @Command(labels = {"test"}, permission = "something", usage = "<player> <number (optional)")
    public void execute(Player player, Player target, @OptionalParam Integer test) {
        player.sendMessage("You have ran this command on " + target.getName() + " with integer " + test);
    }

}
