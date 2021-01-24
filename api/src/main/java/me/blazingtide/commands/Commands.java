package me.blazingtide.commands;

import me.blazingtide.commands.command.builder.CommandBuilder;
import me.blazingtide.commands.command.builder.CommandBuilderImpl;
import me.blazingtide.commands.service.CommandService;
import me.blazingtide.commands.service.CommandServiceBuilder;
import me.blazingtide.commands.service.CommandServiceBuilderImpl;

public class Commands {

    private static CommandService commandService;

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
}
