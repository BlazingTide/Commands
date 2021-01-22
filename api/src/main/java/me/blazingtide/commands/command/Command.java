package me.blazingtide.commands.command;

import me.blazingtide.commands.permission.PermissionHolder;
import me.blazingtide.commands.builder.CommandBuilder;

public interface Command extends PermissionHolder {

    CommandBuilder clone();

}
