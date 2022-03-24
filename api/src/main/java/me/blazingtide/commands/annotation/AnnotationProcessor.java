package me.blazingtide.commands.annotation;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.agent.CommandInjectionAgent;
import me.blazingtide.commands.argument.CommandArguments;
import me.blazingtide.commands.argument.cursor.NonNullArgumentCursor;
import me.blazingtide.commands.command.AnnotationCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.service.CommandService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AnnotationProcessor {

    public static List<Command> createCommands(Object object) {
        final List<Command> commands = new ArrayList<>();

        for (Method method : object.getClass().getMethods()) {
            final me.blazingtide.commands.annotation.Command annotation = method.getAnnotation(me.blazingtide.commands.annotation.Command.class);

            if (annotation == null) continue;

            final Parameter[] parameters = method.getParameters();

            if (parameters.length <= 0) {
                Commands.getCommandService().getAgent().getLogger().severe("Invalid annotation command setup! Method is missing parameters. [" + Arrays.toString(annotation.labels()) + "]");
                continue;
            }

            final List<Class<?>> params = Arrays.stream(parameters).map(Parameter::getType).collect(Collectors.toList());

            final Consumer<CommandArguments> executor = commandArguments -> {
                final Class<?> senderType = params.get(0);
                final Object sender = commandArguments.sender(senderType);

                final Object[] mappedParameters = new Object[parameters.length];
                mappedParameters[0] = sender;

                for (int i = 1; i < params.size(); i++) {
                    Class<?> clazz = params.get(i);

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

                try {
                    method.invoke(object, mappedParameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };

            final Command command = new AnnotationCommand(
                    executor,
                    Arrays.stream(annotation.labels()).map(Label::of).collect(Collectors.toList()),
                    annotation.usage(),
                    annotation.description(),
                    annotation.permission(),
                    annotation.async(),
                    new ArrayList<>(),
                    method,
                    params);

            final CommandService service = Commands.getCommandService();

            service.getRepository().getCollection().add(command); //Stores the command

            //Injects the command
            if (service.getAgent() instanceof CommandInjectionAgent) {
                ((CommandInjectionAgent) service.getAgent()).inject(command);
            }

            commands.add(command);
        }

        return commands;
    }

}
