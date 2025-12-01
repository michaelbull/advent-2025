package com.github.michaelbull.advent2025.math.distance

import com.github.michaelbull.advent2025.math.Vector2
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Calculates the two-dimensional [Euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance#Two_dimensions)
 * between [positions][Vector2] [p] and [q] in the [x][Vector2.x] and [y][Vector2.y] dimensions.
 */
fun euclideanDistance(p: Vector2, q: Vector2): Int {
    val delta = q - p
    return delta.euclideanDistance()
}

private fun Int.squared(): Double {
    return toDouble().pow(2)
}

fun Vector2.euclideanDistance(): Int {
    return sqrt(x.squared() + y.squared()).toInt()
}

infix fun Vector2.euclideanDistanceTo(other: Vector2): Int {
    return euclideanDistance(this, other)
}
