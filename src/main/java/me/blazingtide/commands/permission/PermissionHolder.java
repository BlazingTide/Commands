package me.blazingtide.commands.permission;

/**
 * This interface represents an object that has a permission linked
 * to it.
 * <p>
 * Most notable usages are {@link me.blazingtide.commands.argument.Argument} and {@link me.blazingtide.commands.command.Command}.
 * </p>
 */
public interface PermissionHolder {

    /**
     * Returns the permission for this holder.
     * If the permission is present then the library will verify whether
     * the user has permission to perform the command or not.
     *
     * <p>The method will return null if permission is not present.</p>
     *
     * @return permission for argument
     */
    default String getPermission() {
        return null;
    }

    /**
     * Checks if the object has a permission linked to it.
     *
     * <p>
     * It will look at {@link #getPermission()} and see if the returned String
     * is null or is non null.
     * Simply, if the permission is null then the holder doesn't have a permission and vise-versa.
     * </p>
     *
     * @return has a permission linked
     */
    default boolean hasPermission() {
        return getPermission() != null;
    }

}
