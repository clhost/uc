package io.clhost.uc.core

import java.util.BitSet
import kotlin.math.absoluteValue

class UniqueIPv4Counter {

    private val positiveBitset = BitSet()
    private val negativeBitset = BitSet()

    fun add(index: Int) {
        if (index >= 0) positiveBitset.set(index)
        if (index < 0) negativeBitset.set(index.absoluteValue)
    }

    fun cardinality() = positiveBitset.cardinality() + negativeBitset.cardinality()
}
