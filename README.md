# Commands [![](https://jitci.com/gh/BlazingTide/Commands/svg)](https://jitci.com/gh/BlazingTide/Commands)

The command library made for all Java-based applications.

### Installation

You can download the library in releases or build it yourself. To use the library inside a plugin, add this to your
pom.xml.

```xml
<dependencies>
    <dependency>
        <groupId>me.blazingtide.commands</groupId>
        <artifactId>api</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### Modules

##### [`api`](https://github.com/BlazingTide/Commands/tree/master/api) - The main api for the project

##### [`spigot`](https://github.com/BlazingTide/Commands/tree/master/spigot) - The spigot module for the project

##### [`kotlin`](https://github.com/BlazingTide/Commands/tree/master/kotlin) - A module for easy creation of commands via Kotlin

##### [`bungeecord`](https://github.com/BlazingTide/Commands/tree/master/bungeecord) - The bungeecord module for the project (In-progress)

## How to use

If you're not using any of the prebuilt plugins then this is how you register the api for usage. This example is used by
the [`spigot`](https://github.com/BlazingTide/Commands/tree/master/spigot) plugin.

```java
Commands.newInstance()
        .agent(SpigotCommandAgent.of(this))
        .repository(CommandRepository.basic())
        .register();
```

## Annotation Example

After multiple requests, this library now supports annotations. You can take advantage of this robust framework using
annotations similar to other popular command frameworks.

Below is an example of the same command built using annotations.

```java
@Command(labels = {"checkxp"}, permission = "command.checkxp", usage = "<player / self>", async = true)
public void execute(CommandSender sender, @OptionalParam @PermissionParam("command.checkxp.other") Player target){
        if(target==null){
            if(!(sender instanceof Player)){
                return;
            }

            final Player player=(Player)sender;

            sender.sendMessage("Your XP: "+player.getExp());
            return;
        }

        sender.sendMessage(target.getName()+"'s XP: "+target.getExp());
}
```

## Legacy Example

The library is simple to use but can be a bit difficult to understand at first. This is the most simplest example of a
command that's used to check the experience of a player or yourself.

```java
final Command checkXpCommand=Commands.begin()
        .label("checkxp") //Set's the label for the command
        .permission("command.checkxp") //Set's the base permission to use the command
        .usage("<player / self>") //Set's the usage for the command if there isn't enough arguments
        .async() //Runs the executor asynchronously
        .execute((arguments)->{ //Executes the command
final Optional<Player> targetOptional=arguments.get(0)
        .allowEmpty() //Allows the argument to be empty and will change the return signature to Optional<Type>
        .permission("command.checkxp.other") //Checks if the argument is supplied that the player has permission to perform this command
        .as(Player.class); //Checks whether an argument is supplied and checks if the argument is a Spigot Player

targetOptional.ifPresentOrElse(player->{
    final CommandSender sender=arguments.sender(CommandSender.class); //Automatically converts the sender object into a CommandSender
    final Player target=targetOptional.get();

    sender.sendMessage(target.getName()+"'s XP: "+target.getExp());
},()->{
        final Player sender=arguments.sender(Player.class); //Automatically converts the sender object into a Player and if the sender isn't a player then the command will stop
    
        sender.sendMessage("Your XP: "+sender.getExp());
    });
}).create(); //Creates the command

checkXpCommand.cloneCommand().label("xp").create(); //Clones the same command but under a different label
```

