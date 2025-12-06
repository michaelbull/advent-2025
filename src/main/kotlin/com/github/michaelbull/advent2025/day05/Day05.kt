package com.github.michaelbull.advent2025.day05

import com.github.michaelbull.advent2025.Puzzle

object Day05 : Puzzle<IngredientDatabase, Long>(day = 5) {

    override fun parse(lines: Sequence<String>): IngredientDatabase {
        return lines.toIngredientDatabase()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: IngredientDatabase): Long {
        return input.countFreshIds()
    }

    fun part2(input: IngredientDatabase): Long {
        return input.countTotalFreshIds()
    }
}
