package com.github.michaelbull.advent2025.day03

import com.github.michaelbull.advent2025.day03.Day03.part1
import com.github.michaelbull.advent2025.day03.Day03.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun `example 1`() {
        val input = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """

        assertEquals(357L, Day03.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """

        assertEquals(3121910778619L, Day03.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(17229L, Day03.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(170520923035051L, Day03.solve(::part2))
    }
}
