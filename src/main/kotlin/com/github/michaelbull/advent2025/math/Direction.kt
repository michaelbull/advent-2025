package com.github.michaelbull.advent2025.math

import java.util.EnumSet

fun Vector2.neighbors(): List<Vector2> {
    return Direction.entries.map(::translateIn)
}

fun Vector2.orthogonals(): List<Vector2> {
    return Direction.CARDINALS.map(::translateIn)
}

fun Vector2.diagonals(): List<Vector2> {
    return Direction.INTERCARDINALS.map(::translateIn)
}

fun Vector2.translateIn(direction: Direction): Vector2 {
    return direction.translate(this)
}

enum class Direction(val translation: Vector2) {
    NORTH(+0, +1),
    EAST(+1, +0),
    SOUTH(+0, -1),
    WEST(-1, +0),
    NORTH_EAST(+1, +1),
    SOUTH_EAST(+1, -1),
    SOUTH_WEST(-1, -1),
    NORTH_WEST(-1, +1);

    constructor(x: Int, y: Int) : this(Vector2(x, y))

    fun translate(other: Vector2): Vector2 {
        return other + translation
    }

    companion object {
        val CARDINALS: EnumSet<Direction> = EnumSet.of(
            NORTH,
            EAST,
            SOUTH,
            WEST,
        )

        val INTERCARDINALS: EnumSet<Direction> = EnumSet.of(
            NORTH_EAST,
            SOUTH_EAST,
            SOUTH_WEST,
            NORTH_WEST,
        )
    }
}
