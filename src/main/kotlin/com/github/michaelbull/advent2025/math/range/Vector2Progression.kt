package com.github.michaelbull.advent2025.math.range

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.advent2025.math.Vector2.Companion.DIMENSIONS
import com.github.michaelbull.advent2025.math.Vector2.Companion.DOWN
import com.github.michaelbull.advent2025.math.Vector2.Companion.ZERO

infix fun Vector2.downTo(to: Vector2): Vector2Progression {
    return Vector2Progression.fromClosedRange(this, to, DOWN)
}

infix fun Vector2Progression.step(step: Vector2): Vector2Progression {
    require(step.x >= 0) { "Step X must be non-negative, was: ${step.x}." }
    require(step.y >= 0) { "Step Y must be non-negative, was: ${step.y}." }

    val progressionStep = Vector2(
        x = if (this.step.x > 0) step.x else -step.x,
        y = if (this.step.y > 0) step.y else -step.y,
    )

    return Vector2Progression.fromClosedRange(first, last, progressionStep)
}

open class Vector2Progression internal constructor(
    start: Vector2,
    endInclusive: Vector2,
    val step: Vector2,
) : Iterable<Vector2> {

    val first: Vector2 = start

    val last: Vector2 = Vector2(
        x = getProgressionLastElement(start, endInclusive, step, Vector2::x),
        y = getProgressionLastElement(start, endInclusive, step, Vector2::y),
    )

    val xRange: IntRange
        get() = first.x..last.x

    val yRange: IntRange
        get() = first.y..last.y

    private val isEmpty = isProgressionEmpty(start, endInclusive, step)

    init {
        require(step != ZERO) { "Step must be non-zero." }
    }

    override fun iterator(): Iterator<Vector2> {
        return if (isEmpty) {
            EmptyIterator
        } else {
            ProgressionIterator()
        }
    }

    open fun isEmpty(): Boolean {
        return isEmpty
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }

    companion object {
        fun fromClosedRange(rangeStart: Vector2, rangeEnd: Vector2, step: Vector2): Vector2Progression {
            return Vector2Progression(rangeStart, rangeEnd, step)
        }
    }

    private object EmptyIterator : Iterator<Vector2> {
        override fun hasNext() = false
        override fun next() = throw NoSuchElementException()
    }

    private inner class ProgressionIterator : Iterator<Vector2> {
        private var hasNext: Boolean = true
        private var next = first

        override fun hasNext(): Boolean = hasNext

        override fun next(): Vector2 {
            val value = next

            if (value == last) {
                if (!hasNext) throw NoSuchElementException()
                hasNext = false
            } else {
                next = next.step()
            }

            return value
        }

        private fun Vector2.step() = when {
            x != last.x -> copy(
                x = x + step.x,
            )

            y != last.y -> copy(
                x = first.x,
                y = y + step.y,
            )

            else -> throw NoSuchElementException()
        }
    }
}

private fun isProgressionEmpty(start: Vector2, endInclusive: Vector2, step: Vector2): Boolean {
    return DIMENSIONS.any { dimension ->
        val startValue = dimension(start)
        val endValue = dimension(endInclusive)
        val stepValue = dimension(step)
        isProgressionEmpty(startValue, endValue, stepValue)
    }
}

private fun isProgressionEmpty(start: Int, end: Int, step: Int): Boolean {
    return when {
        step > 0 -> start > end
        step < 0 -> start < end
        else -> if (start == end) {
            false
        } else {
            throw IllegalArgumentException("Start and end must be equal when step is zero.")
        }
    }
}

private inline fun getProgressionLastElement(
    start: Vector2,
    endInclusive: Vector2,
    step: Vector2,
    dimension: (Vector2) -> Int,
): Int {
    val startValue = dimension(start)
    val endInclusiveValue = dimension(endInclusive)
    val stepValue = dimension(step)

    return when {
        isProgressionEmpty(startValue, endInclusiveValue, stepValue) -> endInclusiveValue
        startValue == endInclusiveValue -> endInclusiveValue
        else -> IntProgression.fromClosedRange(startValue, endInclusiveValue, stepValue).last
    }
}
