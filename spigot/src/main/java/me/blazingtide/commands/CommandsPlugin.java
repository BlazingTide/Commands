package me.blazingtide.commands;

import me.blazingtide.commands.adapters.*;
import me.blazingtide.commands.agent.SpigotCommandAgent;
import me.blazingtide.commands.command.AnnotationCommand;
import me.blazingtide.commands.command.Command;
import me.blazingtide.commands.repository.CommandRepository;
import me.blazingtide.commands.test.AnnotationTest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class CommandsPlugin extends JavaPlugin {

    public static final String SPIGOT_FALLBACK_PREFIX = "commands";

    @Override
    public void onEnable() {
        Commands.newInstance()
                .agent(SpigotCommandAgent.of(this))
                .repository(CommandRepository.basic())
                .register();

        registerDefaults();
        registerTest();
    }

    private void registerDefaults() {
        Commands.registerTypeAdapter(Player.class, new PlayerTypeAdapter());
        Commands.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        Commands.registerTypeAdapter(Long.class, new LongTypeAdapter());
        Commands.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
        Commands.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
    }

    private void registerTest() {
        Commands.registerAnnotations(new AnnotationTest());

        final Command checkXpCommand = Commands.begin()
                .label("checkxp") //Set's the label for the command
                .permission("command.checkxp") //Set's the base permission to use the command
                .usage("<player / self>") //Set's the usage for the command if there isn't enough arguments
                .async() //Runs the executor asynchronously
                .execute((arguments) -> { //Executes the command
                    final Optional<Player> targetOptional = arguments.get(0)
                            .allowEmpty() //Allows the argument to be empty and will change the return signature to OptionalParam<Type>
                            .permission("command.checkxp.other") //Checks if the argument is supplied that the player has permission to perform this command
                            .as(Player.class); //Checks whether an argument is supplied and checks if the argument is a Spigot Player

                    targetOptional.ifPresent(target -> {
                        final CommandSender sender = arguments.sender(CommandSender.class); //Automatically converts the sender object into a CommandSender

                        sender.sendMessage(target.getName() + "'s XP: " + target.getExp());
                    });

                    if (!targetOptional.isPresent()) {
                        final Player sender = arguments.sender(Player.class); //Automatically converts the sender object into a Player and if the sender isn't a player then the command will stop

                        sender.sendMessage("Your XP: " + sender.getExp());
                    }
                }).create(); //Creates the command

        checkXpCommand.cloneCommand().label("xp").create(); //Clones the same command but under a different label
    }

}
