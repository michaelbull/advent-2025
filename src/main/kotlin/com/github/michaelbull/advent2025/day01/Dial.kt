package com.github.michaelbull.advent2025.day01

import kotlin.math.absoluteValue

private typealias DialFunction = (Int, Int) -> Int

data class Dial(
    val value: Int,
    val zeroClickCount: Int,
) {

    fun turn(rotation: Rotation): Dial {
        val (direction, distance) = rotation
        val function = direction.toDialFunction()
        val value = function(this.value, distance)

        return copy(
            value = value.mod(SIZE),
            zeroClickCount = countZeroClicks(value, direction),
        )
    }

    private fun countZeroClicks(value: Int, direction: Direction): Int {
        val startIndex = clickIndexFrom(this.value, direction)
        val endIndex = clickIndexFrom(value, direction)
        val deltaIndex = endIndex - startIndex
        return deltaIndex.absoluteValue
    }

    private fun clickIndexFrom(value: Int, direction: Direction): Int {
        val index = when (direction) {
            Left -> value - 1
            Right -> value
        }

        return index.floorDiv(SIZE)
    }

    private fun Direction.toDialFunction(): DialFunction {
        return when (this) {
            Left -> Int::minus
            Right -> Int::plus
        }
    }

    companion object {
        private const val SIZE = 100

        val DEFAULT = Dial(
            value = 50,
            zeroClickCount = 0,
        )
    }
}
