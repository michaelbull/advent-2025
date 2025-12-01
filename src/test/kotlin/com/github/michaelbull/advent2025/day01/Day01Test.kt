package com.github.michaelbull.advent2025.day01

import com.github.michaelbull.advent2025.day01.Day01.part1
import com.github.michaelbull.advent2025.day01.Day01.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun `example 1`() {
        val input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """

        assertEquals(3, Day01.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """

        assertEquals(6, Day01.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(1076, Day01.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(6379, Day01.solve(::part2))
    }
}
