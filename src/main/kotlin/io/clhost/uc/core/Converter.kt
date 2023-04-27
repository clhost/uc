package io.clhost.uc.core

class Converter {

    private val buffer = ByteBuffer(16)
    private val octets = BinaryBuffer(32)

    fun convertIPv4OctetsToInt(bytes: ByteArray): Int {
        octets.reset()
        buffer.reset()
        buffer.copy(bytes)

        val numberOfOctet1 = buffer.octetToDecimal()
        val numberOfOctet2 = buffer.octetToDecimal()
        val numberOfOctet3 = buffer.octetToDecimal()
        val numberOfOctet4 = buffer.octetToDecimal()

        octets.fillOctet(numberOfOctet4)
        octets.fillOctet(numberOfOctet3)
        octets.fillOctet(numberOfOctet2)
        octets.fillOctet(numberOfOctet1)

        var order = 0
        var decimal = 0

        while (octets.canRead()) {
            val bit = octets.read()
            if (bit) decimal += 2.fastPow(order)
            order++
        }

        return decimal
    }

    private fun BinaryBuffer.fillOctet(num: Int) {
        num shr (31 - 8)
        for (i in 7 downTo 0) {
            val k = num shr i
            write((k and 1) > 0)
        }
    }

    private fun ByteBuffer.octetToDecimal(): Int {
        var order = 0
        var decimal = 0
        while (canRead()) {
            val byte = read()
            if (!byte.isDecimal) break
            decimal += byte.asDecimal * 10.fastPow(order++)
        }
        return decimal
    }

    private val Byte.asDecimal: Int
        get() = this - 48

    private val Byte.isDecimal: Boolean
        get() = this - 48 in 0..9

    private fun Int.fastPow(n: Int): Int {
        var a = this
        var b = n
        var result = 1

        while (b > 0) {
            if ((b and 1) == 1) result *= a
            b = b shr 1
            a *= a
        }

        return result
    }
}
