package com.github.michaelbull.advent2025.day02

import kotlin.math.absoluteValue

typealias IdRange = LongRange
typealias RepetitionPredicate = (repetitions: Int) -> Boolean

fun String.toIdRange(): IdRange {
    val (start, endInclusive) = split("-").map(String::toLong)
    return start..endInclusive
}

inline fun List<IdRange>.sumInvalidIds(crossinline predicate: RepetitionPredicate): Long {
    val maxId = maxOf(IdRange::last)

    return invalidIds(predicate)
        .takeWhile { id -> id <= maxId }
        .filter(::contains)
        .sum()
}

inline fun invalidIds(crossinline predicate: RepetitionPredicate): Sequence<Long> {
    return generateSequence(2, Int::inc).flatMap { digits ->
        val sequenceLengths = 1..digits / 2

        sequenceLengths
            .filter { sequenceLength -> digits isDivisibleBy sequenceLength && predicate(digits / sequenceLength) }
            .flatMap { sequenceLength -> invalidIds(sequenceLength, digits) }
            .toSortedSet()
    }
}

infix fun Int.isDivisibleBy(divisor: Int): Boolean {
    return this % divisor == 0
}

fun invalidIds(sequenceLength: Int, digits: Int): List<Long> {
    require(sequenceLength >= 1)

    val repetitions = digits / sequenceLength
    val range = sequenceRange(sequenceLength)

    return range.map { sequence ->
        sequence.nthRepetition(sequenceLength, repetitions - 1)
    }
}

operator fun List<IdRange>.contains(id: Long): Boolean {
    return any { range -> id in range }
}

private fun Long.nthRepetition(sequenceLength: Int, index: Int): Long {
    return repetitions(sequenceLength).elementAt(index)
}

private fun sequenceRange(length: Int): IdRange {
    val min = if (length == 1) 1L else (length - 1).pow10()
    val max = length.pow10() - 1
    return min..max
}

private fun Long.repetitions(length: Int): Sequence<Long> {
    val multiplier = length.pow10()

    return generateSequence(this) {
        this + (it * multiplier)
    }
}

private fun powersOf10(): Sequence<Long> {
    return generateSequence(1L) {
        it * 10
    }
}

private fun Int.pow10(): Long {
    return powersOf10().elementAt(absoluteValue)
}
