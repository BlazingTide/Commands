package me.blazingtide.commands.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;

import java.util.Objects;
import java.util.function.Consumer;

public class CommandBuilderImpl implements CommandBuilder {

    private Consumer<CommandArguments> executor;
    private Label label;
    private String usage;
    private String permission;
    private boolean async;

    @Override
    public CommandBuilder label(String label) {
        Objects.requireNonNull(label);
        this.label = Label.of(label);
        return this;
    }

    @Override
    public CommandBuilder usage(String usage) {
        Objects.requireNonNull(usage);
        this.usage = usage;
        return this;
    }

    @Override
    public CommandBuilder execute(Consumer<CommandArguments> executor) {
        Objects.requireNonNull(executor);
        this.executor = executor;
        return this;
    }

    @Override
    public CommandBuilder permission(String permission) {
        Objects.requireNonNull(permission);
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
        return new Command() {
            @Override
            public Label getLabel() {
                return label;
            }

            @Override
            public String getUsage() {
                return usage;
            }

            @Override
            public Consumer<CommandArguments> getExecutor() {
                return executor;
            }

            @Override
            public boolean isAsync() {
                return async;
            }

            @Override
            public String getPermission() {
                return permission;
            }
        };
    }
}
