package me.blazingtide.commands.conditions;

import me.blazingtide.commands.sender.Sender;

public interface Condition {

    boolean test(Sender sender);

}
