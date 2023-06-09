package io.clhost.uc.ext

import com.github.ajalt.clikt.core.CliktError
import kotlin.system.exitProcess

class OptionMustBePresent(
    options: Set<String>
) : RuntimeException("Error: option $options requires a value")

fun handleException(block: () -> Unit) = try {
    block()
} catch (ex: Exception) {
    if (ex is CliException || ex is CliktError) {
        println(ex.message.red)
    }

    if (ex !is CliException) {
        println("\nInternal error: ${ex.message}".red)
    }

    exitProcess(1)
}

abstract class CliException(message: String) : RuntimeException(message)
