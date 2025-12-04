package com.github.michaelbull.advent2025.day04

import com.github.michaelbull.advent2025.day04.Day04.part1
import com.github.michaelbull.advent2025.day04.Day04.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun `example 1`() {
        val input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """

        assertEquals(13, Day04.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """

        assertEquals(43, Day04.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(1416, Day04.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(9086, Day04.solve(::part2))
    }
}
