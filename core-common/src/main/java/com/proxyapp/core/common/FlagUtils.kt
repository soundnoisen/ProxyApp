package com.proxyapp.core.common

object FlagUtils {
    fun isoToEmojiFlag(iso: String): String {
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(iso, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(iso, 1) - asciiOffset + flagOffset
        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    }
}