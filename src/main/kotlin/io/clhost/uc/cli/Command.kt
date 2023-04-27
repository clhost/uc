package io.clhost.uc.cli

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.types.long
import io.clhost.uc.core.Converter
import io.clhost.uc.core.UniqueIPv4Counter
import io.clhost.uc.ext.PoorCliktCommand
import io.clhost.uc.ext.ensureNotNull
import io.clhost.uc.ext.green
import io.clhost.uc.ext.pureOption
import io.clhost.uc.ext.yellow
import io.clhost.uc.core.forEveryLine
import io.clhost.uc.ext.lightCyan
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

class CountUniqueCommand : PoorCliktCommand(
    name = "count",
    help = "Count unique IPv4 addresses from specified file."
) {

    private val path by pureOption("-p", "--path")
        .help("Path to the file with IPv4 addresses. Hope you're not lying here.")
        .ensureNotNull()

    private val delay by pureOption("-d", "--delay")
        .help("Delay between each step of progress in seconds. Default 60.")
        .long()
        .default(60)

    private val ipv4counter = object : LineCounter {
        private val count = AtomicLong(0)
        override fun inc() { count.incrementAndGet() }
        override fun count() = count.get()
    }

    private val printer = Thread {
        val sleepTime = Duration.ofSeconds(delay).toMillis()
        while (true) {
            echo("Count of processed IPv4 addresses is: ${"${ipv4counter.count()}".yellow}")
            Thread.sleep(sleepTime)
        }
    }.apply { isDaemon = true }

    override fun execute() {
        val reader = BufferedReader(FileReader(File(path)))
        val counter = UniqueIPv4Counter()
        val converter = Converter()

        printer.start()

        reader.forEveryLine {
            val index = converter.convertIPv4OctetsToInt(it.toByteArray())
            counter.add(index)
            ipv4counter.inc()
        }

        necho("Job has been done!")
        echo("Count of unique IPv4 addresses is: ${"${counter.cardinality()}".green}")
        echo("Count of processed IPv4 addresses is: ${"${ipv4counter.count()}".lightCyan}")
    }
}

interface LineCounter {
    fun inc()
    fun count(): Long
}
