# Commands [<!--lint ignore no-dead-urls-->![GitHub Actions status | BlazingTide/Commands](https://github.com/BlazingTide/Commands/workflows/Maven%20Build/badge.svg)](https://github.com/sdras/awesome-actions/actions?workflow=Lint+Awesome+List)

> The command library made for all Java-based applications.

#### Discord Support: https://discord.gg/2y6EGBfJtq 

# Features

* Annotation based command creation
* Automatic mapping of arguments
* Optional arguments with null support
* Auto complete / Tab complete for each type
* Auto Complete for individual arguments ([@AutoComplete](https://github.com/BlazingTide/Commands/blob/master/api/src/main/java/me/blazingtide/commands/annotation/AutoComplete.java))
* Permissions for individual arguments
* No plugin.yml command registration required
* Automatic help message generation
* Async & Sync at the command declaration level
* String Concatenation for last parameter
* Spigot Support
* Velocity Support
* & more!

### Installation

You can download the library in releases or build it yourself. To use the library inside a plugin, add this to your
pom.xml.

```xml
<!-- Spigot -->
<dependencies>
    <dependency>
        <groupId>me.blazingtide.commands</groupId>
        <artifactId>spigot</artifactId>
        <version>1.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<!-- Velocity -->
<dependencies>
    <dependency>
        <groupId>me.blazingtide.commands</groupId>
        <artifactId>velocity</artifactId>
        <version>1.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Modules

##### [`api`](https://github.com/BlazingTide/Commands/tree/master/api) - The main api for the project

##### [`spigot`](https://github.com/BlazingTide/Commands/tree/master/spigot) - The spigot module for the project

##### [`kotlin`](https://github.com/BlazingTide/Commands/tree/master/kotlin) - A module for easy creation of commands via Kotlin

##### [`velocity`](https://github.com/BlazingTide/Commands/tree/master/velocity) - The velocity module for the project

## How to use (Standalone API)

Upload the spigot or velocity plugin on your server. It's that simple! You can begin using the library right away.

## Annotation Example

After multiple requests, this library now supports annotations. You can take advantage of this robust framework using
annotations similar to other popular command frameworks.

Below is an example of the same command built using annotations.

```java

// /item give example
@Command(labels = {"item give"}, permission = "command.give", usage = "<item> <player>")
public void execute(CommandSender sender, Material material, @PermissionParam("command.give.other") @OptionalParam Player target) { 
    //Adding @OptionalParam on the last parameter allows that parameter to be nullable
    //Adding @PermissionParam will require the sender to have that permission to use that parameter    
    
    if (target == null) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player to perform this command!");
            return;
        }

        final Player player = (Player) sender;

        player.getInventory().addItem(new ItemStack(material));
        sender.sendMessage("You have received " + material);
        return;
    }
    
    target.getInventory().addItem(new ItemStack(material));
    target.sendMessage("You have received " + material);
}
```

### Register Command

```java
Commands.registerAnnotations(new YourCommand());
```

The library will register your command and handle everything else.