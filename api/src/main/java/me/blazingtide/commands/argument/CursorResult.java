package me.blazingtide.commands.argument;

import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.permission.PermissionHolder;

public interface CursorResult extends PermissionHolder {

    Label getLabel();

}
