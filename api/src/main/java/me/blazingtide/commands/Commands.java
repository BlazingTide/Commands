package me.blazingtide.commands;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.annotation.AnnotationProcessor;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.command.builder.CommandBuilder;
import me.blazingtide.commands.command.builder.CommandBuilderImpl;
import me.blazingtide.commands.service.CommandService;
import me.blazingtide.commands.service.CommandServiceBuilder;
import me.blazingtide.commands.service.CommandServiceBuilderImpl;

import java.util.List;

public class Commands {

    private static CommandService commandService;

    public static List<Command> registerAnnotations(Object object) {
        return AnnotationProcessor.createCommands(object);
    }

    public static CommandBuilder begin() {
        return new CommandBuilderImpl();
    }

    public static CommandServiceBuilder newInstance() {
        return new CommandServiceBuilderImpl();
    }

    public static CommandService getCommandService() {
        return commandService;
    }

    public static void setCommandService(CommandService service) throws IllegalAccessException {
        if (commandService != null) {
            throw new IllegalAccessException("The command service is already set");
        }

        commandService = service;
    }

    public static <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<T> adapter) {
        getCommandService().getTypeAdapterMap().put(clazz, adapter);
    }
}
