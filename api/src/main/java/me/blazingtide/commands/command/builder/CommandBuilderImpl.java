package me.blazingtide.commands.command.builder;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.agent.CommandInjectionAgent;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.service.CommandService;

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
        Objects.requireNonNull(label);
        Objects.requireNonNull(usage);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(permission);

        final Command command = new Command() { //Creates a new command instance
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

        final CommandService service = Commands.getCommandService();

        service.getRepository().getCollection().add(command); //Stores the command

        //Injects the command
        if (service.getAgent() instanceof CommandInjectionAgent) {
            ((CommandInjectionAgent) service.getAgent()).inject(command);
        }

        return command;
    }
}