package me.blazingtide.commands.annotation;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.agent.CommandInjectionAgent;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.command.AnnotationCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.exception.sender.CommandSenderException;
import me.blazingtide.commands.sender.Sender;
import me.blazingtide.commands.service.CommandService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AnnotationProcessor {

    public static List<Command> createCommands(Object object, String[] parents) {
        final List<Command> commands = new ArrayList<>();

        for (Method method : object.getClass().getMethods()) {
            final me.blazingtide.commands.annotation.Command annotation = method.getAnnotation(me.blazingtide.commands.annotation.Command.class);

            if (annotation == null) continue;

            final Parameter[] parameters = method.getParameters();

            if (parameters.length < 1) {
                Commands.getCommandService().getAgent().getLogger().severe("Invalid annotation command setup! Method is missing parameters. [" + Arrays.toString(annotation.labels()) + "]");
                continue;
            }

            final List<Class<?>> params = Arrays.stream(parameters).map(Parameter::getType).collect(Collectors.toList());

            for (String label : annotation.labels()) {
                if (parents != null) {
                    for (String parent : parents) {
                        commands.add(processForLabel((parent + " " + label.toLowerCase()).trim(), params, parameters, method, object, annotation));
                    }
                    continue;
                }

                commands.add(processForLabel(label.toLowerCase(), params, parameters, method, object, annotation));
            }
        }

        return commands;
    }

    private static Command processForLabel(String label, List<Class<?>> params, Parameter[] parameters, Method method, Object object, me.blazingtide.commands.annotation.Command annotation) {
        final String[] split = label.split(" ");
        boolean isSubCommand = split.length > 1;

        final Command command = new AnnotationCommand(
                createExecutor(parameters, method),
                List.of(split[split.length - 1]),
                annotation.usage(),
                annotation.description(),
                annotation.permission(),
                annotation.async(),
                new ArrayList<>(),
                method,
                params
        );

        //Determine if this is a sub command
        if (!isSubCommand) {
            final CommandService service = Commands.getCommandService();

            service.getRepository().add(command); //Stores the command

            //Injects the command
            if (service.getAgent() instanceof CommandInjectionAgent) {
                ((CommandInjectionAgent) service.getAgent()).inject(command);
            }
        } else {
            final Command parent = traverse(split, 0, null);

            //Remove duplicate subcommands if they have the same labels & description, created for hot reloading plugins during runtime
            parent.getSubCommands().removeIf(command1 -> new HashSet<>(command1.getLabels()).containsAll(command.getLabels()) && command1.getDescription().equals(command.getDescription()));

            parent.getSubCommands().add(command);
        }

        return command;
    }

    private static Command traverse(String[] label, int index, Command parent) {
        if (parent == null || index == 0) {
            return traverse(label, 1, createPrimaryParent(label));
        }

        if (index == label.length - 1) {
            return parent; //Final parent
        }

        for (Command subCommand : parent.getSubCommands()) {
            if (subCommand.getLabels().contains(label[index])) {
                return traverse(label, index + 1, subCommand);
            }
        }

        final Command sub = Commands.begin()
                .label(label[index])
                .subcommand()
                .create();

        //Remove duplicate subcommands if they have the same labels & description, created for hot reloading plugins during runtime
        parent.getSubCommands().removeIf(command1 -> new HashSet<>(command1.getLabels()).containsAll(sub.getLabels()) && command1.getDescription().equals(sub.getDescription()));
        parent.getSubCommands().add(sub);

        return traverse(label, index + 1, sub);
    }

    private static Command createPrimaryParent(String[] labelSplit) {
        final Command command = Commands.getCommandService().getRepository().getCollection().get(labelSplit[0]);

        if (command != null) {
            return command;
        }

        return Commands.begin()
                .label(labelSplit[0])
                .create();
    }

    private static Consumer<CommandArguments> createExecutor(Parameter[] parameters, Method method) {
        return commandArguments -> {
            final Class<?> senderType = parameters[0].getType();

            Object sender = commandArguments.sender(senderType);

            //Custom senders/dispatchers
            if (Commands.getDispatcherService().getDispatcherProviders().containsKey(senderType)) {
                final Optional<?> optional = Commands.getDispatcherService().getDispatcherProviders().get(senderType)
                        .provide(Sender.of(sender));

                if (optional.isPresent()) {
                    sender = optional.get();
                } else {
                    throw new CommandSenderException("Dispatcher returned an empty optional.");
                }
            }

            final Object[] mappedParameters = new Object[parameters.length];
            mappedParameters[0] = sender;

            for (int i = 1; i < parameters.length; i++) {
                Class<?> clazz = parameters[i].getType();

                final NonNullArgumentCursor cursor = commandArguments.get(i - 1);
                final PermissionParam permissionAnnotation = parameters[i].getAnnotation(PermissionParam.class);
                final OptionalParam optionalAnnotation = parameters[i].getAnnotation(OptionalParam.class);

                if (permissionAnnotation != null) {
                    cursor.permission(permissionAnnotation.value());
                }

                if (optionalAnnotation != null) {
                    mappedParameters[i] = cursor.allowEmpty().as(clazz).orElse(null);
                } else {
                    mappedParameters[i] = cursor.as(clazz);
                }
            }

            //If the last parameter type is a string then collect all the other strings in the rest of the argument
            //and use that as a parameter rather than just that 1 word
            if (parameters[parameters.length - 1].getType() == String.class && commandArguments.getArguments().length > parameters.length - 1) {
                final StringBuilder builder = new StringBuilder();

                for (int i = parameters.length - 2; i < commandArguments.getArguments().length; i++) {
                    builder.append(commandArguments.getArguments()[i].getLabel()).append(" ");
                }

                mappedParameters[parameters.length - 1] = builder.toString().trim();
            }

            try {
                method.invoke(sender, mappedParameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

}
