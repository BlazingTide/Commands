package me.blazingtide.commands

import me.blazingtide.commands.command.Command

@Deprecated(message = "Use @Command annotation framework")
inline fun command(builder: KCommandBuilder.() -> Unit): Command {
    val conf = KCommandBuilder().apply(builder)

    val builder = Commands.begin()
        .label(conf._label)

    if (conf._permission != null) {
        builder.permission(conf._permission)
    }

    if (conf._usage != null) {
        builder.usage(conf._usage)
    }

    if (conf._async) {
        builder.async()
    }
    builder.description(conf._description)

    if (conf._executor != null) {
        builder.execute { conf._executor?.let { it1 -> it1(it) } }
    }

    val command = builder.create()

    for (subCommand in conf.subCommands) {
        val sub = KCommandBuilder().apply(subCommand)

        val builder = Commands.begin()
            .label(sub._label)

        if (sub._permission != null) {
            builder.permission(sub._permission)
        }

        if (sub._usage != null) {
            builder.usage(sub._usage)
        }

        if (sub._async) {
            builder.async()
        }

        builder.description(sub._description)
        if (sub._executor != null) {
            builder.execute { sub._executor?.let { it1 -> it1(it) } }
        }
        builder.subcommand()

        command.subCommands.add(builder.create())
    }

    return command
}
