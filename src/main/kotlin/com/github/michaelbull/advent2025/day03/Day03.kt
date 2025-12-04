package com.github.michaelbull.advent2025.day03

import com.github.michaelbull.advent2025.Puzzle

object Day03 : Puzzle<Sequence<Bank>, Long>(day = 3) {

    override fun parse(lines: Sequence<String>): Sequence<Bank> {
        return lines.map(::Bank)
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(banks: Sequence<Bank>): Long {
        return banks.totalJoltage(digits = 2)
    }

    fun part2(banks: Sequence<Bank>): Long {
        return banks.totalJoltage(digits = 12)
    }
}
