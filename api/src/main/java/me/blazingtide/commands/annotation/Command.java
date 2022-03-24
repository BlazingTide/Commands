package me.blazingtide.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * Sets the label for the command.
     */
    String[] labels();

    /**
     * Sets the description for the command.
     */
    String description() default "A command";

    /**
     * Sets the permission for the command.
     */
    String permission() default "";

    /**
     * Determines whether the command should run async
     */
    boolean async() default false;

    /**
     * Sets the usage for the command.
     */
    String usage() default "";

}