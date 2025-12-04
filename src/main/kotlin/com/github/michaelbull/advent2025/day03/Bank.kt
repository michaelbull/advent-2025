package com.github.michaelbull.advent2025.day03

@JvmInline
value class Bank(private val string: String) {
    fun maxJoltage(digits: Int): Long {
        val digitRange = 0 until digits

        val (_, joltage) = digitRange.fold(0 to "") { (start, joltage), selected ->
            val remaining = digits - selected
            val endInclusive = string.length - remaining
            val range = start..endInclusive

            val maxIndex = range.maxBy(string::get)
            val nextIndex = maxIndex + 1

            val digit = string[maxIndex]
            val nextJoltage = joltage + digit

            nextIndex to nextJoltage
        }

        return joltage.toLong()
    }
}

fun Sequence<Bank>.totalJoltage(digits: Int): Long {
    return sumOf { bank ->
        bank.maxJoltage(digits)
    }
}
