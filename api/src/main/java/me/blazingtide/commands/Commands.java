package me.blazingtide.commands;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.annotation.AnnotationProcessor;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.command.builder.CommandBuilder;
import me.blazingtide.commands.command.builder.CommandBuilderImpl;
import me.blazingtide.commands.sender.dispatcher.DispatcherProvider;
import me.blazingtide.commands.sender.dispatcher.DispatcherService;
import me.blazingtide.commands.sender.dispatcher.DispatcherServiceImpl;
import me.blazingtide.commands.service.CommandService;
import me.blazingtide.commands.service.CommandServiceBuilder;
import me.blazingtide.commands.service.CommandServiceBuilderImpl;

import java.util.List;

public class Commands {

    private static final DispatcherService dispatcherService = new DispatcherServiceImpl();
    private static CommandService commandService;

    public static List<Command> registerAnnotations(Object object) {
        return AnnotationProcessor.createCommands(object, null);
    }

    public static List<Command> registerAnnotations(Object object, String... parents) {
        return AnnotationProcessor.createCommands(object, parents);
    }

    public static <T> void registerDispatcher(Class<T> clazz,DispatcherProvider<T> provider) {
        dispatcherService.registerDispatcherProvider(clazz,provider);
    }

    public static void unregisterCommands(String... labels) {
        commandService.getAgent().unregisterCommands(labels);
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

    public static CommandService getCommandService() {
        return commandService;
    }

    @Deprecated
    public static CommandBuilder begin() {
        return new CommandBuilderImpl();
    }

    public static CommandServiceBuilder newInstance() {
        return new CommandServiceBuilderImpl();
    }

    public static DispatcherService getDispatcherService() {
        return dispatcherService;
    }
}
