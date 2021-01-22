package me.blazingtide.commands;

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

        CommandService.begin()
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


        CommandException:
        -> CommandSenderException -> CommandSenderCastException

        -> CommandArgumentException -> CommandPermissionException & CommandArgumentEmptyException & CommandArgumentCastException

     */

}
