### Commands


## Example Usage
The library is simple to use but can be a bit difficult to understand at first.
This is the most simplest example of a command that's used to check the experience of
a player or yourself.

```java
final Command checkXpCommand = Commands.begin()
        .label("checkxp") //Set's the label for the command
        .permission("command.checkxp") //Set's the base permission to use the command
        .usage("<player / self>") //Set's the usage for the command if there isn't enough arguments
        .execute((arguments) -> { //Executes the command
                final Optional<Player> targetOptional = arguments.get(0)
                        .allowEmpty()
                        .permission("command.checkxp.other") //Checks if the argument is supplied that the player has permission to perform this command
                        .as(Player.class); //Checks whether an argument is supplied and checks if the argument is a Spigot Player

                targetOptional.ifPresentOrElse(player -> {
                        final CommandSender sender = arguments.sender(CommandSender.class); //Automatically converts the sender object into a CommandSender
                        final Player target = targetOptional.get();

                        sender.sendMessage(target.getName() + "'s XP: " + target.getExp());
                }, () -> {
                        final Player sender = arguments.sender(Player.class); //Automatically converts the sender object into a Player and if the sender isn't a player then the command will stop

                        sender.sendMessage("Your XP: " + sender.getExp());
                });
        }).create(); //Creates the command

checkXpCommand.clone().label("xp").create(); //Clones the same command but under a different label
```