package com.github.michaelbull.advent2025.math.range

fun Int.symmetricRange(): IntRange {
    return -this..this
}

operator fun Int.rem(progression: IntProgression): Int {
    return wrap(progression, Int::rem)
}

fun Int.mod(progression: IntProgression): Int {
    return wrap(progression, Int::mod)
}

private inline fun Int.wrap(progression: IntProgression, operation: Int.(Int) -> Int): Int {
    val first = progression.first
    val offset = this - first
    val size = (progression.last - first) + 1
    return offset.operation(size) + first
}
