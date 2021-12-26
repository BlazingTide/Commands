package me.blazingtide.commands.command.sub.builder;

import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.sub.SubCommand;
import me.blazingtide.commands.label.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SubCommandBuilderImpl implements SubCommandBuilder {

    private Consumer<CommandArguments> executor;
    private List<Label> labels = new ArrayList<>();
    private String usage = "";
    private String description = "";
    private String permission = "";
    private boolean async;
    private List<SubCommand> subCommands = new ArrayList<>();

    @Override
    public SubCommandBuilder label(String label) {
        Objects.requireNonNull(label);
        this.labels.add(Label.of(label));
        return this;
    }

    @Override
    public SubCommandBuilder description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }

    @Override
    public SubCommandBuilder usage(String usage) {
        Objects.requireNonNull(usage);
        this.usage = usage;
        return this;
    }

    @Override
    public SubCommandBuilder execute(Consumer<CommandArguments> executor) {
        Objects.requireNonNull(executor);
        this.executor = executor;
        return this;
    }

    @Override
    public SubCommandBuilder permission(String permission) {
        Objects.requireNonNull(permission);
        this.permission = permission;
        return this;
    }

    @Override
    public SubCommandBuilder subCommand(SubCommand subCommand) {
        Objects.requireNonNull(subCommand);
        this.subCommands.add(subCommand);
        return this;
    }

    @Override
    public SubCommandBuilder async() {
        this.async = true;
        return this;
    }

    @Override
    public SubCommand create() {
        if (labels.isEmpty()) {
            throw new NullPointerException("There were no labels specified.");
        }
        Objects.requireNonNull(executor, "Command execution has not been defined.");

        return new SubCommand() { //Creates a new command instance
            @Override
            public List<Label> getLabels() {
                return labels;
            }

            @Override
            public String getUsage() {
                return usage;
            }

            @Override
            public String getDescription() {
                return description;
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
            public List<SubCommand> getSubCommands() {
                return subCommands;
            }

            @Override
            public String getPermission() {
                return permission;
            }
        };
    }
}
