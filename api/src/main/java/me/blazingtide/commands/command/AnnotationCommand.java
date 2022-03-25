package me.blazingtide.commands.command;

import me.blazingtide.commands.argument.CommandArguments;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

public class AnnotationCommand implements Command {

    private final Consumer<CommandArguments> executor;
    private final List<String> labels;
    private final String usage;
    private final String description;
    private final String permission;
    private final boolean async;
    private final List<Command> subCommands;

    private final Method method;
    private final List<Class<?>> parameters;

    public AnnotationCommand(Consumer<CommandArguments> executor, List<String> labels, String usage, String description, String permission, boolean async, List<Command> subCommands, Method method, List<Class<?>> parameters) {
        this.executor = executor;
        this.labels = labels;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.async = async;
        this.subCommands = subCommands;
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public Consumer<CommandArguments> getExecutor() {
        return executor;
    }

    @Override
    public List<String> getLabels() {
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
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public List<Command> getSubCommands() {
        return subCommands;
    }

    public List<Class<?>> getParameters() {
        return parameters;
    }

    public Method getMethod() {
        return method;
    }
}
