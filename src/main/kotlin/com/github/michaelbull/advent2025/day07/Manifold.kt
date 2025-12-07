package com.github.michaelbull.advent2025.day07

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.advent2025.math.grid.CharGrid
import com.github.michaelbull.advent2025.math.grid.toCharGrid

fun Sequence<String>.toManifold(): Manifold {
    return Manifold(toCharGrid())
}

data class Manifold(
    val grid: CharGrid,
) {

    fun countSplits(): Long {
        val start = start()
        var curr = mutableSetOf(start.x)
        var next = mutableSetOf<Int>()

        var splits = 0L

        for (y in yRangeAbove(start)) {
            for (x in curr) {
                if (isSplitterAt(x, y)) {
                    splits++
                }

                for (x in traverse(x, y)) {
                    next += x
                }
            }

            if (next.isEmpty()) {
                break
            }

            curr = next.also { next = curr }
            next.clear()
        }

        return splits
    }

    fun countTimelines(): Long {
        val start = start()
        var curr = mutableMapOf(start.x to 1L)
        var next = mutableMapOf<Int, Long>()

        for (y in yRangeAbove(start)) {
            for ((x, count) in curr) {
                for (x in traverse(x, y)) {
                    next.merge(x, count, Long::plus)
                }
            }

            if (next.isEmpty()) {
                break
            }

            curr = next.also { next = curr }
            next.clear()
        }

        return curr.values.sum()
    }

    private fun yRangeAbove(position: Vector2): IntRange {
        return (position.y + 1) until grid.height
    }

    private fun traverse(x: Int, y: Int) = sequence {
        if (isSplitterAt(x, y)) {
            if (x - 1 in grid.xRange) {
                yield(x - 1)
            }

            if (x + 1 in grid.xRange) {
                yield(x + 1)
            }
        } else {
            yield(x)
        }
    }

    private fun isSplitterAt(x: Int, y: Int): Boolean {
        return grid[x, y] == SPLITTER
    }

    private fun isStartAt(x: Int, y: Int): Boolean {
        return grid[x, y] == START
    }

    private fun start(): Vector2 {
        return grid
            .first { (position, _) -> isStartAt(position.x, position.y) }
            .first
    }

    private companion object {
        private const val START = 'S'
        private const val SPLITTER = '^'
    }
}
