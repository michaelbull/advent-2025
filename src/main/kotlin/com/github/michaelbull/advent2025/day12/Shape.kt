package com.github.michaelbull.advent2025.day12

import com.github.michaelbull.advent2025.math.Vector2

fun List<String>.toShape(): Shape {
    val cells = buildSet {
        this@toShape.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                if (char == '#') {
                    add(Vector2(x, y))
                }
            }
        }
    }

    return Shape(cells)
}

data class Shape(val cells: Set<Vector2>) {

    fun orientations() = buildSet {
        var current = this@Shape

        repeat(4) {
            add(current.normalize())
            add(current.flipHorizontal().normalize())
            current = current.rotateCw()
        }
    }

    private fun rotateCw(): Shape {
        return Shape(cells.map(Vector2::rotateCw).toSet())
    }

    private fun flipHorizontal(): Shape {
        return Shape(cells.map(Vector2::flipHorizontal).toSet())
    }

    private fun normalize(): Shape {
        val min = cells.fold(Vector2.MAX_VALUE, Vector2::coerceAtMost)
        return Shape(cells.map { cell -> cell - min }.toSet())
    }

    companion object {
        val HEADER_REGEX = """(\d+):""".toRegex()
    }
}

private fun Vector2.rotateCw(): Vector2 {
    return Vector2(-y, x)
}

private fun Vector2.flipHorizontal(): Vector2 {
    return Vector2(-x, y)
}
