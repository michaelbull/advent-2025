package com.github.michaelbull.advent2025.day09

data class VerticalBoundary(
    val column: Int,
    val rowRange: IntRange,
) {

    val columnRange: IntRange
        get() = column..column

    fun crossesRow(row: Int): Boolean {
        return row in rowRange && row < rowRange.last
    }
}

fun List<VerticalBoundary>.scanlineFill(row: Int): Sequence<IntRange> {
    return asSequence()
        .filter { boundary -> boundary.crossesRow(row) }
        .map(VerticalBoundary::column)
        .sorted()
        .chunked(2)
        .filter { it.size == 2 }
        .map(List<Int>::toIntRange)
}

private fun List<Int>.toIntRange(): IntRange {
    val (start, end) = this
    return start..end
}
