package com.github.michaelbull.advent2025.math.distance

import com.github.michaelbull.advent2025.math.Vector2
import kotlin.math.abs

/**
 * Calculates the two-dimensional [Manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry)
 * between [positions][Vector2] [p] and [q] in the [x][Vector2.x] and [y][Vector2.y] dimensions.
 */
fun manhattanDistance(p: Vector2, q: Vector2): Int {
    val delta = q - p
    return delta.manhattanDistance()
}

fun Vector2.manhattanDistance(): Int {
    return abs(x) + abs(y)
}

infix fun Vector2.manhattanDistanceTo(other: Vector2): Int {
    return manhattanDistance(this, other)
}
