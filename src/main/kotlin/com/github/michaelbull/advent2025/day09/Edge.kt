package com.github.michaelbull.advent2025.day09

import com.github.michaelbull.advent2025.math.Vector2

data class Edge(
    val start: Vector2,
    val end: Vector2,
) {
    val vertical: Boolean
        get() = start.x == end.x

    val columnRange: IntRange
        get() = start.x.coerceAtMost(end.x)..start.x.coerceAtLeast(end.x)

    val rowRange: IntRange
        get() = start.y.coerceAtMost(end.y)..start.y.coerceAtLeast(end.y)

    fun toVerticalBoundary(): VerticalBoundary {
        return VerticalBoundary(
            column = start.x,
            rowRange = rowRange,
        )
    }
}
