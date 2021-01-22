package me.blazingtide.commands.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;

import java.util.function.Consumer;

public interface CommandBuilder {

    CommandBuilder label(String label);

    CommandBuilder usage(String usage);

    CommandBuilder execute(Consumer<CommandArguments> executor);

    Command create();

}
