package com.github.michaelbull.advent2025.day01

data class Rotation(
    val direction: Direction,
    val distance: Int,
)

private val DIRECTION_REGEX = "[LR]".toRegex()
private val DISTANCE_REGEX = "\\d+".toRegex()
private val ROTATION_REGEX = "($DIRECTION_REGEX)($DISTANCE_REGEX)".toRegex()

fun String.toRotation(): Rotation {
    val match = requireNotNull(ROTATION_REGEX.matchEntire(this)) {
        "$this must match $ROTATION_REGEX"
    }

    val (directionChar, distance) = match.destructured

    return Rotation(
        direction = directionChar.toDirection(),
        distance = distance.toInt(),
    )
}
