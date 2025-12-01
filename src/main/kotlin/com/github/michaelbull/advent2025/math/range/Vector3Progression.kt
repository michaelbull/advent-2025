package com.github.michaelbull.advent2025.math.range

import com.github.michaelbull.advent2025.math.Vector3
import com.github.michaelbull.advent2025.math.Vector3.Companion.DIMENSIONS
import com.github.michaelbull.advent2025.math.Vector3.Companion.DOWN
import com.github.michaelbull.advent2025.math.Vector3.Companion.ZERO

infix fun Vector3.downTo(to: Vector3): Vector3Progression {
    return Vector3Progression.fromClosedRange(this, to, DOWN)
}

infix fun Vector3Progression.step(step: Vector3): Vector3Progression {
    require(step.x >= 0) { "Step X must be non-negative, was: ${step.x}." }
    require(step.y >= 0) { "Step Y must be non-negative, was: ${step.y}." }
    require(step.z >= 0) { "Step Z must be non-negative, was: ${step.z}." }

    val progressionStep = Vector3(
        x = if (this.step.x > 0) step.x else -step.x,
        y = if (this.step.y > 0) step.y else -step.y,
        z = if (this.step.z > 0) step.z else -step.z
    )

    return Vector3Progression.fromClosedRange(first, last, progressionStep)
}

open class Vector3Progression internal constructor(
    start: Vector3,
    endInclusive: Vector3,
    val step: Vector3,
) : Iterable<Vector3> {

    val first: Vector3 = start

    val last: Vector3 = Vector3(
        x = getProgressionLastElement(start, endInclusive, step, Vector3::x),
        y = getProgressionLastElement(start, endInclusive, step, Vector3::y),
        z = getProgressionLastElement(start, endInclusive, step, Vector3::z),
    )

    val xRange: IntRange
        get() = first.x..last.x

    val yRange: IntRange
        get() = first.y..last.y

    val zRange: IntRange
        get() = first.z..last.z

    private val isEmpty = isProgressionEmpty(start, endInclusive, step)

    init {
        require(step != ZERO) { "Step must be non-zero." }
    }

    override fun iterator(): Iterator<Vector3> {
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
        fun fromClosedRange(rangeStart: Vector3, rangeEnd: Vector3, step: Vector3): Vector3Progression {
            return Vector3Progression(rangeStart, rangeEnd, step)
        }
    }

    private object EmptyIterator : Iterator<Vector3> {
        override fun hasNext() = false
        override fun next() = throw NoSuchElementException()
    }

    private inner class ProgressionIterator : Iterator<Vector3> {
        private var hasNext: Boolean = true
        private var next = first

        override fun hasNext(): Boolean = hasNext

        override fun next(): Vector3 {
            val value = next

            if (value == last) {
                if (!hasNext) throw NoSuchElementException()
                hasNext = false
            } else {
                next = next.step()
            }

            return value
        }

        private fun Vector3.step() = when {
            x != last.x -> copy(
                x = x + step.x,
            )

            y != last.y -> copy(
                x = first.x,
                y = y + step.y,
            )

            z != last.z -> copy(
                x = first.x,
                y = first.y,
                z = z + step.z,
            )

            else -> throw NoSuchElementException()
        }
    }
}

private fun isProgressionEmpty(start: Vector3, endInclusive: Vector3, step: Vector3): Boolean {
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
    start: Vector3,
    endInclusive: Vector3,
    step: Vector3,
    dimension: (Vector3) -> Int,
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
