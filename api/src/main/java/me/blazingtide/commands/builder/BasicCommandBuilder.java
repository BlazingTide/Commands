package me.blazingtide.commands.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;

import java.util.function.Consumer;

public class BasicCommandBuilder implements CommandBuilder {

    private Consumer<CommandArguments> executor;
    private Label label;
    private String usage;
    private String permission;
    private boolean async;

    @Override
    public CommandBuilder label(String label) {
        this.label = Label.of(label);
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
    public CommandBuilder async() {
        this.async = true;
        return this;
    }

    @Override
    public Command create() {
        return null;
    }
}
