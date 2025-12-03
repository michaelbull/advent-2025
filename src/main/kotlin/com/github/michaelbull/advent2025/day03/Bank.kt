package com.github.michaelbull.advent2025.day03

typealias Bank = String

fun Sequence<Bank>.totalJoltage(digits: Int): Long {
    return sumOf { bank ->
        bank.maxJoltage(digits)
    }
}

private fun Bank.maxJoltage(digits: Int): Long {
    val digitRange = 0 until digits

    val (_, joltage) = digitRange.fold(0 to "") { (start, joltage), selected ->
        val remaining = digits - selected
        val endInclusive = length - remaining
        val range = start..endInclusive

        val maxIndex = range.maxBy(::get)
        val nextIndex = maxIndex + 1

        val digit = this[maxIndex]
        val nextJoltage = joltage + digit

        nextIndex to nextJoltage
    }

    return joltage.toLong()
}
