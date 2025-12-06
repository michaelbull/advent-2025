package com.github.michaelbull.advent2025.day06

import com.github.michaelbull.advent2025.Puzzle

object Day06 : Puzzle<Worksheet, Long>(day = 6) {

    override fun parse(lines: Sequence<String>): Worksheet {
        return lines.toWorksheet()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: Worksheet): Long {
        return input.sumAnswers(Problem::operands)
    }

    fun part2(input: Worksheet): Long {
        return input.sumAnswers(Problem::cephalopodOperands)
    }
}
