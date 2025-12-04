package com.github.michaelbull.advent2025.day01

import com.github.michaelbull.advent2025.Puzzle

object Day01 : Puzzle<Sequence<Rotation>, Int>(day = 1) {

    override fun parse(lines: Sequence<String>): Sequence<Rotation> {
        return lines.map(String::toRotation)
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: Sequence<Rotation>): Int {
        return input
            .turnSequence()
            .count(Dial::isZero)
    }

    fun part2(input: Sequence<Rotation>): Int {
        return input
            .turnSequence()
            .sumOf(Dial::zeroClickCount)
    }
}
