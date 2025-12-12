package com.github.michaelbull.advent2025.day12

import com.github.michaelbull.advent2025.Puzzle

object Day12 : Puzzle<Summary, Int>(day = 12) {

    override fun parse(lines: Sequence<String>): Summary {
        return lines.toSummary()
    }

    override fun solutions() = listOf(
        ::part1,
    )

    fun part1(summary: Summary): Int {
        return summary.countFittingRegions()
    }
}
