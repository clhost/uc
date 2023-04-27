package io.clhost.uc.core

class ByteBuffer(capacity: Int) {
    private var size: Int = 0
    private val bytes: ByteArray = ByteArray(capacity)

    fun write(byte: Byte) {
        if (size == bytes.size) throw IllegalStateException("Buffer is full.")
        bytes[size++] = byte
    }

    fun read(): Byte {
        if (size == -1) throw IllegalStateException("Buffer is empty.")
        return bytes[--size]
    }

    fun reset() {
        size = 0
    }

    fun canRead() = size > 0

    fun canWrite() = size < bytes.size

    fun copy(bytes: ByteArray) {
        var i = 0
        while (canWrite()) {
            write(bytes[i++])
            if (i == bytes.size) break
        }
    }
}

class BinaryBuffer(capacity: Int) {
    private var size: Int = 0
    private val bits = BooleanArray(capacity)

    fun write(bit: Boolean) {
        if (size == bits.size) throw IllegalStateException("Buffer is full.")
        bits[size++] = bit
    }

    fun read(): Boolean {
        if (size == -1) throw IllegalStateException("Buffer is empty.")
        return bits[--size]
    }

    fun reset() {
        size = 0
    }

    fun canRead() = size > 0
}
