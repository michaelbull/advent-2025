package com.github.michaelbull.advent2025.day05

import com.github.michaelbull.advent2025.day05.Day05.part1
import com.github.michaelbull.advent2025.day05.Day05.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {

    @Test
    fun `example 1`() {
        val input = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
        """

        assertEquals(3L, Day05.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
        """

        assertEquals(14L, Day05.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(868L, Day05.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(354143734113772L, Day05.solve(::part2))
    }
}
