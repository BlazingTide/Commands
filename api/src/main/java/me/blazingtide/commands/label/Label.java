package me.blazingtide.commands.label;

/**
 * Labels are objects that contain a String value.
 *
 * <p>
 * Label objects are not mutable so the values inside will
 * never change.
 * </p>
 */
public interface Label {

    /**
     * Returns a new label object with the provided string.
     *
     * @param str the value
     * @return a new label object
     */
    static Label of(String str) {
        return () -> str;
    }

    /**
     * The String value associated with the Label object.
     *
     * @return the string value
     */
    String getValue();

}
