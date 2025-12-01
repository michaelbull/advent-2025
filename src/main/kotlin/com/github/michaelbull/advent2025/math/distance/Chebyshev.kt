package com.github.michaelbull.advent2025.math.distance

import com.github.michaelbull.advent2025.math.Vector2
import kotlin.math.abs
import kotlin.math.max

/**
 * Calculates the two-dimensional [Chebyshev distance](https://en.wikipedia.org/wiki/Chebyshev_distance)
 * between [positions][Vector2] [p] and [q] in the [x][Vector2.x] and [y][Vector2.y] dimensions.
 */
fun chebyshevDistance(p: Vector2, q: Vector2): Int {
    val delta = q - p
    return delta.chebyshevDistance()
}

fun Vector2.chebyshevDistance(): Int {
    return max(abs(x), abs(y))
}

infix fun Vector2.chebyshevDistanceTo(other: Vector2): Int {
    return chebyshevDistance(this, other)
}
