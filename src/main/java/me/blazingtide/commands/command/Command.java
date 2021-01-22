package me.blazingtide.commands.command;

import me.blazingtide.commands.builder.CommandBuilder;
import me.blazingtide.commands.permission.PermissionHolder;

public interface Command extends PermissionHolder {

    CommandBuilder clone();

}
