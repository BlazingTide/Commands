package me.blazingtide.commands.argument;

import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.permission.PermissionHolder;

public interface CursorResult extends PermissionHolder {

    Label getLabel();

    static CursorResult of(Label label, String permission) {
        return new CursorResult() {
            @Override
            public Label getLabel() {
                return label;
            }

            @Override
            public String getPermission() {
                return permission;
            }
        };
    }

}
