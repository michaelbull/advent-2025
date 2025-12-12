package com.github.michaelbull.advent2025.day12

import com.github.michaelbull.advent2025.math.Vector2

fun String.toRegion(): Region {
    val match = requireNotNull(Region.REGEX.matchEntire(this)) {
        "$this must match ${Region.REGEX}"
    }

    val (width, height, counts) = match.destructured

    return Region(
        size = Vector2(width.toInt(), height.toInt()),
        shapeCounts = counts.split(" ").map(String::toInt),
    )
}

data class Region(
    val size: Vector2,
    val shapeCounts: List<Int>,
) {

    private val width = size.x
    private val height = size.y
    private val bounds = Vector2.ZERO..<size
    private val area = width * height
    private val indices = 0..<area

    init {
        require(width > 0) { "width must be positive, but was $width" }
        require(height > 0) { "height must be positive, but was $height" }
    }

    infix fun fitsAll(orientations: List<Set<Shape>>): Boolean {
        val state = State(
            orientations = orientations,
            grid = BooleanArray(area),
            skipped = BooleanArray(area),
            remaining = shapeCounts.toIntArray(),
        )

        val totalCells = state.remaining.indices.sumOf { i ->
            val count = state.remaining[i]
            val cellsPerShape = orientations[i].first().cells.size
            count * cellsPerShape
        }

        return state.solve(
            available = area,
            needed = totalCells,
        )
    }

    private inner class State(
        val orientations: List<Set<Shape>>,
        val grid: BooleanArray,
        val skipped: BooleanArray,
        val remaining: IntArray,
    ) {

        fun solve(available: Int, needed: Int): Boolean {
            return when {
                needed == 0 -> true
                needed > available -> false
                else -> when (val emptyIndex = findEmptyIndex()) {
                    null -> false
                    else -> solveAt(emptyIndex, available, needed)
                }
            }
        }

        private fun solveAt(emptyIndex: Int, available: Int, needed: Int): Boolean {
            return when {
                solveByPlacingShapes(emptyIndex, available, needed) -> true
                solveBySkipping(emptyIndex, available, needed) -> true
                else -> false
            }
        }

        private fun solveByPlacingShapes(emptyIndex: Int, available: Int, needed: Int): Boolean {
            val emptyPosition = positionOf(emptyIndex)

            for (shapeIndex in remaining.indices) {
                val count = remaining[shapeIndex]

                if (count == 0) {
                    continue
                }

                val placed = solveByPlacingShape(
                    shapeIndex = shapeIndex,
                    emptyPosition = emptyPosition,
                    available = available,
                    needed = needed,
                )

                if (placed) {
                    return true
                }
            }

            return false
        }

        private fun solveByPlacingShape(
            shapeIndex: Int,
            emptyPosition: Vector2,
            available: Int,
            needed: Int,
        ): Boolean {
            val shapeOrientations = orientations[shapeIndex]
            val size = shapeOrientations.first().cells.size

            for (shape in shapeOrientations) {
                val placed = solveByPlacingOrientation(
                    shapeIndex = shapeIndex,
                    shape = shape,
                    emptyPosition = emptyPosition,
                    size = size,
                    available = available,
                    needed = needed,
                )

                if (placed) {
                    return true
                }
            }

            return false
        }

        private fun solveBySkipping(index: Int, available: Int, needed: Int): Boolean {
            return withSkipped(index) {
                solve(available - 1, needed)
            }
        }

        private fun solveByPlacingOrientation(
            shapeIndex: Int,
            shape: Shape,
            emptyPosition: Vector2,
            size: Int,
            available: Int,
            needed: Int,
        ): Boolean {
            for (cell in shape.cells) {
                val offset = emptyPosition - cell
                val placement = place(shape, offset)

                if (placement != null) {
                    val solved = withDecrementedCount(shapeIndex) {
                        solve(available - size, needed - size)
                    }

                    if (solved) {
                        return true
                    }

                    unplace(placement)
                }
            }

            return false
        }

        private inline fun <T> withSkipped(index: Int, block: () -> T): T {
            skipped[index] = true

            return try {
                block()
            } finally {
                skipped[index] = false
            }
        }

        private inline fun <T> withDecrementedCount(index: Int, block: () -> T): T {
            remaining[index]--

            return try {
                block()
            } finally {
                remaining[index]++
            }
        }

        private fun unplace(positions: IntArray, count: Int = positions.size) {
            for (i in 0..<count) {
                val index = positions[i]
                grid[index] = false
            }
        }

        private fun canPlace(position: Vector2): Boolean {
            return if (position in bounds) {
                val index = indexOf(position)
                val occupied = grid[index]
                val skipped = skipped[index]
                !occupied && !skipped
            } else {
                false
            }
        }

        private fun place(shape: Shape, offset: Vector2): IntArray? {
            val positions = IntArray(shape.cells.size)
            var i = 0

            for (cell in shape.cells) {
                val position = cell + offset

                if (canPlace(position)) {
                    val index = indexOf(position)
                    grid[index] = true
                    positions[i++] = index
                } else {
                    unplace(positions, i)
                    return null
                }
            }

            return positions
        }

        private fun findEmptyIndex(): Int? {
            return indices.find(::isEmpty)
        }

        private fun isEmpty(index: Int): Boolean {
            val isOccupied = grid[index]
            val isSkipped = skipped[index]
            return !isOccupied && !isSkipped
        }

        private fun indexOf(position: Vector2): Int {
            return (position.y * width) + position.x
        }

        private fun positionOf(index: Int): Vector2 {
            return Vector2(
                x = index % width,
                y = index / width,
            )
        }
    }

    companion object {
        val REGEX = """(\d+)x(\d+): (.+)""".toRegex()
    }
}
