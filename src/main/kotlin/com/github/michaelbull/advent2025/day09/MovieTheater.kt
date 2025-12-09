package com.github.michaelbull.advent2025.day09

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.itertools.combinations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

fun Sequence<String>.toMovieTheater(): MovieTheater {
    val redTiles = map(String::toVector2).toList()
    return MovieTheater(redTiles)
}

private fun String.toVector2(): Vector2 {
    val (x, y) = split(",").map(String::toInt)
    return Vector2(x, y)
}

class MovieTheater(
    private val redTiles: List<Vector2>,
) {

    private val minRow: Int
        get() = redTiles.minOf(Vector2::y)

    private val maxRow: Int
        get() = redTiles.maxOf(Vector2::y)

    private val rowRange: IntRange
        get() = minRow..maxRow

    private val coloredColumnRangesByRow by lazy {
        buildColoredTileRanges()
    }

    private val coloredRows by lazy {
        coloredColumnRangesByRow.keys.sorted().toIntArray()
    }

    fun largestRectangleArea(): Long {
        return redTiles
            .rectangleCombinations()
            .maxOf(Rectangle::area)
    }

    suspend fun largestColoredRectangleArea(): Long {
        return coloredRectangleAreas()
            .flowOn(Dispatchers.Default)
            .reduce(::maxOf)
    }

    private fun coloredRectangleAreas() = channelFlow {
        for (rectangle in redTiles.rectangleCombinations()) {
            launch {
                if (rectangle.isColored()) {
                    send(rectangle.area())
                }
            }
        }
    }

    private fun Rectangle.isColored(): Boolean {
        val startIndex = coloredRows.firstIndexAtLeast(minRow)
        val endIndex = coloredRows.lastIndexAtMost(maxRow)

        val rowIndices = startIndex..endIndex

        return if (rowIndices.isEmpty()) {
            false
        } else {
            rowIndices.none { index ->
                val row = coloredRows[index]
                val ranges = coloredColumnRangesByRow[row]
                ranges == null || ranges.isUncovered(columnRange)
            }
        }
    }

    private fun buildColoredTileRanges(): Map<Int, List<IntRange>> {
        return collectColumnRangesByRow().mapValues { (_, ranges) ->
            ranges.merge()
        }
    }

    private fun collectColumnRangesByRow() = buildMap<Int, MutableList<IntRange>> {
        val wrappedVertices = redTiles + redTiles.first()
        val edges = wrappedVertices.zipWithNext(::Edge)
        val (verticalEdges, horizontalEdges) = edges.partition(Edge::vertical)
        val verticalBoundaries = verticalEdges.map(Edge::toVerticalBoundary)

        fun add(row: Int, columnRange: IntRange) {
            getOrPut(row, ::mutableListOf) += columnRange
        }

        /* add vertical boundary tiles */
        for (boundary in verticalBoundaries) {
            for (row in boundary.rowRange) {
                add(row, boundary.columnRange)
            }
        }

        /* add horizontal boundary tiles */
        for (edge in horizontalEdges) {
            add(edge.start.y, edge.columnRange)
        }

        /* fill interior tiles */
        for (row in rowRange) {
            for (range in verticalBoundaries.scanlineFill(row)) {
                add(row, range)
            }
        }
    }

    private fun IntArray.firstIndexAtLeast(value: Int): Int {
        val index = binarySearch(value)

        return if (index < 0) {
            -(index + 1)
        } else {
            index
        }
    }

    private fun IntArray.lastIndexAtMost(value: Int): Int {
        val index = binarySearch(value)

        return if (index < 0) {
            -(index + 1) - 1
        } else {
            index
        }
    }

    private fun List<IntRange>.merge(): List<IntRange> {
        if (isEmpty()) {
            return emptyList()
        }

        val sorted = sortedBy(IntRange::first)

        return buildList {
            var current = sorted[0]

            for (index in 1..<sorted.size) {
                val range = sorted[index]

                if (range.first <= current.last + 1) {
                    current = current.first..current.last.coerceAtLeast(range.last)
                } else {
                    add(current)
                    current = range
                }
            }

            add(current)
        }
    }

    private fun List<IntRange>.isUncovered(target: IntRange): Boolean {
        var low = 0
        var high = lastIndex

        while (low <= high) {
            val mid = (low + high) ushr 1
            val range = this[mid]

            when {
                target.first < range.first -> high = mid - 1
                target.first > range.last -> low = mid + 1
                else -> return target.last > range.last
            }
        }

        return true
    }
}

private fun List<Vector2>.rectangleCombinations(): Sequence<Rectangle> {
    return combinations(
        k = 2,
        combination = ::rectangleCombination,
    )
}

private fun List<Vector2>.rectangleCombination(indices: IntArray, k: Int): Rectangle {
    require(k == 2) { "k must be 2, but was $k" }

    val (first, second) = indices

    return Rectangle(
        a = get(first),
        b = get(second),
    )
}
