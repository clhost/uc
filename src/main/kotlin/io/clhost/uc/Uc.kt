package io.clhost.uc

import com.github.ajalt.clikt.completion.completionOption
import com.github.ajalt.clikt.core.subcommands
import io.clhost.uc.cli.CountUniqueCommand
import io.clhost.uc.ext.PoorCliktCommand
import io.clhost.uc.ext.handleException

class Uc : PoorCliktCommand(printHelpOnEmptyArgs = true) {

    init {
        completionOption(hidden = true)
        subcommands(CountUniqueCommand())
    }

    override fun execute() {}
}

fun main(args: Array<String>) = handleException {
    Uc().main(args)
}
