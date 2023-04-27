package io.clhost.uc.core

import java.io.BufferedReader

fun BufferedReader.forEveryLine(block: (String) -> Unit) {
    while (true) {
        val line = readLine()
        if (line.isNullOrEmpty()) return
        block(line)
    }
}
