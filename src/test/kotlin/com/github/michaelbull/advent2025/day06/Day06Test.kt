package com.github.michaelbull.advent2025.day06

import com.github.michaelbull.advent2025.day06.Day06.part1
import com.github.michaelbull.advent2025.day06.Day06.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun `example 1`() {
        val input = """
            123 328  51 64
             45 64  387 23
              6 98  215 314
            *   +   *   +
        """

        assertEquals(4277556L, Day06.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            123 328  51 64
             45 64  387 23
              6 98  215 314
            *   +   *   +
        """

        assertEquals(3263827L, Day06.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(6209956042374L, Day06.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(12608160008022L, Day06.solve(::part2))
    }
}
