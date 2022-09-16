package me.blazingtide.commands.testing;

import me.blazingtide.commands.annotation.Command;

public class DispatcherTest {

    @Command(labels = {"test"})
    public void testMe(WorldDispatcher dispatcher, String test) {
        System.out.println(dispatcher.player().getName() + " " + dispatcher.world().getName() + " " + test);
    }

}
