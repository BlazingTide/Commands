package me.blazingtide.commands.autocomplete;

import me.blazingtide.commands.Commands;
import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.annotation.AutoComplete;
import me.blazingtide.commands.command.AnnotationCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.sender.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface AutoCompleteProvider {

    default Pair<Command, Integer> traverse(String[] arguments, int index, Command parent) {
        //Don't worry about 0, it'll never be 0
        if (arguments.length == 0) {
            return null;
        }

        /*  binary tree model, makes it easier to visualize & test

                            [root: 0, {''}]
                        |           |
                [sub command: 1, {'stuff', ''}]   [sub command]
                    |
               [sub command: 2, {'stuff', 't1', ''}]
         */

        //Root
        if (arguments.length == 1) {
            return new Pair(parent, index);
        }

        if (index + 1 == arguments.length) {
            return new Pair(parent, index);
        }

        if (parent.getSubCommands().isEmpty()) {
            return new Pair(parent, index);
        }

        for (Command subCommand : parent.getSubCommands()) {
            if (subCommand.getLabels().stream().anyMatch(label -> label.equalsIgnoreCase(arguments[index]))) {
                return traverse(arguments, index + 1, subCommand); //0: stuff
            }
        }

        return new Pair(parent, index);
    }

    default List<String> getParameterAutoComplete(Command command, int depth, String[] args, String lastWords, Sender sender) {
        int index = args.length - depth;

        if (!(command instanceof AnnotationCommand)) {
            return null;
        }

        final AnnotationCommand annotationCommand = (AnnotationCommand) command;

        final List<Class<?>> parameters = annotationCommand.getParameters();

        var loc = index >= parameters.size() ? parameters.size() - 1 : index;
        final Class<?> param = parameters.get(loc);

        if (param == null) {
            System.out.println("Is null here");
            return null; //Just fallback to default if this occurs, but it should never occur
        }

        final AutoComplete autoCompleteAnnotation = annotationCommand.getMethod().getParameters()[loc].getAnnotation(AutoComplete.class);

        if (autoCompleteAnnotation != null) {
            return List.of(autoCompleteAnnotation.value());
        }

        final TypeAdapter<?> adapter = Commands.getCommandService().getTypeAdapterMap().getOrDefault(param, null);

        if (adapter != null) {
            return adapter.getAutoComplete(lastWords, () -> sender);
        }


        return null;
    }

    default List<String> processInput(String[] args, Sender sender, Command commandParent) {
        final String lastWords = args.length == 0 ? "" : args[args.length - 1];

        final Pair<Command, Integer> traverse = traverse(args, 0, commandParent);
        final Command command = traverse.first();
        final Integer depth = traverse.second();

        final List<String> autoComplete = new ArrayList<>();

        if (command == null) {
            return Arrays.asList("error", "unknown", "tab complete");
        }

        if (command.getSubCommands().isEmpty()) {
            final List<String> complete = getParameterAutoComplete(command, depth, args, lastWords, sender);

            if (complete != null) {
                autoComplete.addAll(complete);
            }
        }

        if (!command.getSubCommands().isEmpty()) {
            command.getSubCommands().forEach(sub -> autoComplete.addAll(sub.getLabels()));
        }

        final List<String> toReturn = autoComplete.stream().filter(str -> StringUtil.startsWithIgnoreCase(str, lastWords)).collect(Collectors.toList());

        if (!toReturn.isEmpty()) {
            toReturn.sort(String.CASE_INSENSITIVE_ORDER);

            return toReturn;
        }

        return null;
    }


}
