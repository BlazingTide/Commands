package me.blazingtide.commands.argument;

import me.blazingtide.commands.permission.PermissionHolder;

public interface CursorResult extends PermissionHolder {

    static CursorResult of(String label, String permission) {
        return new CursorResult() {
            @Override
            public String getLabel() {
                return label;
            }

            @Override
            public String getPermission() {
                return permission;
            }
        };
    }

    String getLabel();

}
