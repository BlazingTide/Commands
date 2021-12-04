package me.blazingtide.commands

import me.blazingtide.commands.argument.CommandArguments

class KCommandBuilder {

    var _label: String? = null
    var _permission: String? = null
    var _usage: String? = null
    var _async = false
    lateinit var _executor: (args: CommandArguments) -> Unit

    fun label(str: String) {
        this._label = str
    }

    fun permission(str: String) {
        this._permission = str
    }

    fun usage(str: String) {
        this._usage = str
    }

    fun async(executor: (args: CommandArguments) -> Unit) {
        this._executor = executor
        this._async = true
    }

}