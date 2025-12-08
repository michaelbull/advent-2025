package com.github.michaelbull.advent2025.day04

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.advent2025.math.grid.CharGrid
import com.github.michaelbull.advent2025.math.grid.toCharGrid
import com.github.michaelbull.advent2025.math.neighbors

fun Sequence<String>.toRollDiagram(): RollDiagram {
    return RollDiagram(toCharGrid())
}

class RollDiagram(
    private val grid: CharGrid,
) {

    private val rollPositions: Set<Vector2> = grid
        .positions()
        .filterTo(mutableSetOf(), grid::isRollAt)

    fun countAccessible(): Int {
        return rollPositions.countAccessible()
    }

    fun countRemovable(): Int {
        val remaining = rollPositions.toMutableSet()
        var total = 0

        while (true) {
            val accessible = remaining.filterAccessible()

            if (accessible.isNotEmpty()) {
                remaining.removeAll(accessible)
                total += accessible.size
            } else {
                break
            }
        }

        return total
    }

    private fun Set<Vector2>.countAccessible(): Int {
        return count { position ->
            isAccessible(position)
        }
    }

    private fun Set<Vector2>.filterAccessible(): Set<Vector2> {
        return filterTo(mutableSetOf()) { position ->
            isAccessible(position)
        }
    }

    private fun Set<Vector2>.isAccessible(position: Vector2): Boolean {
        return position.countNeighboursIn(this) < 4
    }

    private fun Vector2.countNeighboursIn(candidates: Set<Vector2>): Int {
        return neighbors().count(candidates::contains)
    }
}

private const val ROLL = '@'

private fun CharGrid.isRollAt(position: Vector2): Boolean {
    return this[position] == ROLL
}
