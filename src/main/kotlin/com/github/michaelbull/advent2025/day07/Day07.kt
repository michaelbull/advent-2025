package com.github.michaelbull.advent2025.day07

import com.github.michaelbull.advent2025.Puzzle

object Day07 : Puzzle<Manifold, Long>(day = 7) {

    override fun parse(lines: Sequence<String>): Manifold {
        return lines.toManifold()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: Manifold): Long {
        return input.countSplits()
    }

    fun part2(input: Manifold): Long {
        return input.countTimelines()
    }
}
