package com.github.michaelbull.advent2025.day09

import com.github.michaelbull.advent2025.day09.Day09.part1
import com.github.michaelbull.advent2025.day09.Day09.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    @Test
    fun `example 1`() {
        val input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """

        assertEquals(50L, Day09.solve(::part1, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(4750092396, Day09.solve(::part1))
    }

    @Test
    fun `example 2`() {
        val input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """

        assertEquals(24L, Day09.solve(::part2, input))
    }

    @Test
    fun `answer 2`() {
        assertEquals(1468516555, Day09.solve(::part2))
    }
}
