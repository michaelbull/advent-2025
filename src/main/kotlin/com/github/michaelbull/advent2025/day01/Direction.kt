package com.github.michaelbull.advent2025.day01

sealed interface Direction {
    companion object {
        val REGEX = """[LR]""".toRegex()
    }
}

data object Left : Direction
data object Right : Direction

fun Char.toDirection(): Direction {
    return when (this) {
        'L' -> Left
        'R' -> Right
        else -> throw IllegalArgumentException("unknown direction $this")
    }
}
