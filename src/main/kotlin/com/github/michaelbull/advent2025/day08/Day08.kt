package com.github.michaelbull.advent2025.day08

import com.github.michaelbull.advent2025.Puzzle

object Day08 : Puzzle<Playground, Long>(day = 8) {

    override fun parse(lines: Sequence<String>): Playground {
        return lines.toPlayground()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(playground: Playground): Long {
        return playground.largestCircuitsProduct(connections = 1000)
    }

    fun part2(playground: Playground): Long {
        return playground.finalConnectionProduct()
    }
}
