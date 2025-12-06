package com.github.michaelbull.advent2025.day06

fun String.safeSubstring(range: IntRange): String {
    val startInclusive = range.first.coerceIn(0, length)
    val endExclusive = (range.last + 1).coerceIn(0, length)
    return substring(startInclusive, endExclusive)
}
