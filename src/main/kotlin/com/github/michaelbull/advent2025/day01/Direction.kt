package com.github.michaelbull.advent2025.day01

sealed class Direction
data object Left : Direction()
data object Right : Direction()

fun String.toDirection(): Direction {
    return when (this) {
        "L" -> Left
        "R" -> Right
        else -> throw IllegalArgumentException("unknown direction $this")
    }
}

