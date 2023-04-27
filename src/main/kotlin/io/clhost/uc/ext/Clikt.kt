package io.clhost.uc.ext

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ParameterHolder
import com.github.ajalt.clikt.parameters.options.NullableOption
import com.github.ajalt.clikt.parameters.options.OptionWithValues
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.transformAll

fun <EachT : Any, ValueT> NullableOption<EachT, ValueT>.ensureNotNull(): OptionWithValues<EachT, EachT, ValueT> {
    return transformAll { it.lastOrNull() ?: throw OptionMustBePresent(names) }
}

fun ParameterHolder.pureOption(
    vararg names: String,
    help: String = "",
    hidden: Boolean = false,
    envvar: String? = null,
    helpTags: Map<String, String> = emptyMap(),
    completionCandidates: CompletionCandidates? = null,
    valueSourceKey: String? = null,
) = option(
    names = names,
    help = help,
    metavar = "",
    hidden = hidden,
    envvar = envvar,
    helpTags = helpTags,
    completionCandidates = completionCandidates,
    valueSourceKey = valueSourceKey,
)

abstract class PoorCliktCommand(
    help: String = "",
    epilog: String = "",
    name: String? = null,
    invokeWithoutSubcommand: Boolean = false,
    printHelpOnEmptyArgs: Boolean = false,
    helpTags: Map<String, String> = emptyMap(),
    autoCompleteEnvvar: String? = "",
    allowMultipleSubcommands: Boolean = false,
    treatUnknownOptionsAsArgs: Boolean = false
) : CliktCommand(
    help = help,
    epilog = epilog,
    name = name,
    invokeWithoutSubcommand = invokeWithoutSubcommand,
    printHelpOnEmptyArgs = printHelpOnEmptyArgs,
    helpTags = helpTags,
    autoCompleteEnvvar = autoCompleteEnvvar,
    allowMultipleSubcommands = allowMultipleSubcommands,
    treatUnknownOptionsAsArgs = treatUnknownOptionsAsArgs
) {

    override fun run() { execute() }

    abstract fun execute(): Any

    protected fun necho(message: Any) = echo("\n$message")
}
