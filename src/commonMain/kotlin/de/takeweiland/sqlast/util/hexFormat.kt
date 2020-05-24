package de.takeweiland.sqlast.util

fun ByteArray.toHexString(): String {
    return joinToString(separator = "") {
        (it.toInt() and 0xFF).toString(16).padStart(2, ' ')
    }
}