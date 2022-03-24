package me.blazingtide.commands.command.builder;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.agent.CommandInjectionAgent;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.service.CommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CommandBuilderImpl implements CommandBuilder {

    private Consumer<CommandArguments> executor;
    private List<Label> labels = new ArrayList<>();
    private String usage = "";
    private String description = "";
    private String permission = "";
    private boolean async;
    private List<Command> subCommands = new ArrayList<>();

    @Override
    public CommandBuilder label(String label) {
        Objects.requireNonNull(label);
        this.labels.add(Label.of(label));
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
    public CommandBuilder subCommand(Command subCommand) {
        Objects.requireNonNull(subCommand);
        this.subCommands.add(subCommand);
        return this;
    }

    @Override
    public CommandBuilder description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }

    @Override
    public CommandBuilder async() {
        this.async = true;
        return this;
    }

    @Override
    public Command create() {
        if (labels.isEmpty()) {
            throw new NullPointerException("There were no labels specified.");
        }
//        if (subCommands.isEmpty()) {
//            Objects.requireNonNull(executor, "Command execution has not been defined.");
//        }

        final Command command = new Command() { //Creates a new command instance
            @Override
            public List<Label> getLabels() {
                return labels;
            }

            @Override
            public String getDescription() {
                return description;
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
            public List<Command> getSubCommands() {
                return subCommands;
            }

            @Override
            public String getPermission() {
                return permission;
            }
        };

        final CommandService service = Commands.getCommandService();

        service.getRepository().getCollection().add(command); //Stores the command

        //Injects the command
        if (service.getAgent() instanceof CommandInjectionAgent) {
            ((CommandInjectionAgent) service.getAgent()).inject(command);
        }

        return command;
    }
}
