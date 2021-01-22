package me.blazingtide.commands.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;

import java.util.function.Consumer;

public class BasicCommandBuilder implements CommandBuilder {

    private Consumer<CommandArguments> executor;
    private String label;
    private String usage;
    private String permission;

    @Override
    public CommandBuilder label(String label) {
        this.label = label;
        return this;
    }

    @Override
    public CommandBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public CommandBuilder execute(Consumer<CommandArguments> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public CommandBuilder permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public Command create() {
        return null;
    }
}
