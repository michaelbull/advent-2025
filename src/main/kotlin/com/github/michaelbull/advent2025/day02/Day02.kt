package com.github.michaelbull.advent2025.day02

import com.github.michaelbull.advent2025.Puzzle

object Day02 : Puzzle<List<IdRange>, Long>(day = 2) {

    override fun parse(lines: Sequence<String>): List<IdRange> {
        return lines
            .first()
            .split(",")
            .map(String::toIdRange)
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: List<IdRange>): Long {
        return input.sumInvalidIds { repetitions ->
            repetitions == 2
        }
    }

    fun part2(input: List<IdRange>): Long {
        return input.sumInvalidIds { repetitions ->
            repetitions >= 2
        }
    }
}
