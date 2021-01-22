package me.blazingtide.commands;

import me.blazingtide.commands.builder.BasicCommandBuilder;
import me.blazingtide.commands.builder.CommandBuilder;

public class Commands {

    public static CommandBuilder begin() {
        return new BasicCommandBuilder();
    }

}
