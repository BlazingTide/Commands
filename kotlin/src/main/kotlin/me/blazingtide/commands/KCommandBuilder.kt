package me.blazingtide.commands

import me.blazingtide.commands.argument.CommandArguments

open class KCommandBuilder {

    var _label: String? = null
    var _permission: String? = null
    var _usage: String? = null
    var _async = false
    var subCommands = arrayListOf<KCommandBuilder.() -> Unit>()
    var _executor: ((CommandArguments) -> Unit?)? = null

    fun label(str: String) {
        this._label = str
    }

    fun permission(str: String) {
        this._permission = str
    }

    fun usage(str: String) {
        this._usage = str
    }

    fun execute(executor: (args: CommandArguments) -> Unit) {
        this._executor = executor
    }

    fun subCommand(builder: KCommandBuilder.() -> Unit) {
        subCommands.add(builder)
    }

    fun async(executor: (args: CommandArguments) -> Unit) {
        this._executor = executor
        this._async = true
    }

}