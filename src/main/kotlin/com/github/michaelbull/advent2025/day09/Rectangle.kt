package com.github.michaelbull.advent2025.day09

import com.github.michaelbull.advent2025.math.Vector2

data class Rectangle(
    val columnRange: IntRange,
    val rowRange: IntRange,
) {

    constructor(a: Vector2, b: Vector2) : this(
        columnRange = a.x.coerceAtMost(b.x)..a.x.coerceAtLeast(b.x),
        rowRange = a.y.coerceAtMost(b.y)..a.y.coerceAtLeast(b.y),
    )

    val minColumn: Int
        get() = columnRange.first

    val maxColumn: Int
        get() = columnRange.last

    val minRow: Int
        get() = rowRange.first

    val maxRow: Int
        get() = rowRange.last

    fun area(): Long {
        val width = maxColumn - minColumn + 1
        val height = maxRow - minRow + 1
        return width.toLong() * height
    }
}
