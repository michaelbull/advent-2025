package com.github.michaelbull.advent2025.day03

@JvmInline
value class Bank(private val value: String) {
    tailrec fun maxJoltage(digits: Int, start: Int = 0, joltage: Long = 0L): Long {
        return if (digits == 0) {
            joltage
        } else {
            val endInclusive = value.length - digits
            val range = start..endInclusive
            val maxIndex = range.maxBy(value::get)
            val digit = value[maxIndex].digitToInt()
            maxJoltage(digits - 1, maxIndex + 1, (joltage * 10) + digit)
        }
    }
}

fun Sequence<Bank>.totalJoltage(digits: Int): Long {
    return sumOf { bank ->
        bank.maxJoltage(digits)
    }
}
