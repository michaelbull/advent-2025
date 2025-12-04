package com.github.michaelbull.advent2025.day04

import com.github.michaelbull.advent2025.Puzzle

object Day04 : Puzzle<RollDiagram, Int>(day = 4) {

    override fun parse(lines: Sequence<String>): RollDiagram {
        return lines.toRollDiagram()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(diagram: RollDiagram): Int {
        return diagram.countAccessible()
    }

    fun part2(diagram: RollDiagram): Int {
        return diagram.countRemovable()
    }
}
