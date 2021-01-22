package me.blazingtide.commands;

import me.blazingtide.commands.builder.CommandBuilder;

public class Commands {

    /*
            CommandService.begin()
            .label("broadcast")
            .usage("<message>")
            .permission("broadcast.message")
            .execute((arguments) -> {
                final Player sender = arguments.sender(Player.class);
                final String argument = arguments.get(0).as(String.class);

                Bukkit.broadcastMessage(argument);
               }).create();

        Command command = CommandService.begin()
            .label("checkHealth")
            .usage("<message>")
            .execute((arguments) -> {
                final Player sender = arguments.sender(Player.class);

                final Optional<Player> argument = arguments.get(0)
                                                            .allowEmpty()
                                                            .permission("checkhealth.other")
                                                            .as(String.class);

                if (argument.isPresent()) {
                    sender.sendMessage(argument.get().getHealth());
               } else {
                    sender.sendMessage(sender.getHealth());
               }
           }).create();

        command.clone().label("hp").create(); // Returns a builder object, allowing us to create a command that's the same but with a different label

        CommandException:
        -> CommandSenderException -> CommandSenderCastException

        -> CommandArgumentException -> CommandPermissionException & CommandArgumentEmptyException & CommandArgumentCastException

     */

    public static CommandBuilder begin() {
        return null;
    }

}
