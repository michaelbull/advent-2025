package com.github.michaelbull.advent2025.math.range

fun Int.symmetricRange(): IntRange {
    return -this..this
}

operator fun Int.rem(progression: IntProgression): Int {
    val first = progression.first
    val last = progression.last
    return (this - first).rem(last - first + 1) + first
}

fun Int.mod(progression: IntProgression): Int {
    val first = progression.first
    val last = progression.last
    return (this - first).mod(last - first + 1) + first
}
