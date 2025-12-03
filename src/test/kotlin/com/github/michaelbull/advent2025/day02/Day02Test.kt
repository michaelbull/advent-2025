package com.github.michaelbull.advent2025.day02

import com.github.michaelbull.advent2025.day02.Day02.part1
import com.github.michaelbull.advent2025.day02.Day02.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    @Test
    fun `example 1`() {
        val input = """
            11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
        """

        assertEquals(1227775554, Day02.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
        """

        assertEquals(4174379265, Day02.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(23534117921, Day02.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(31755323497, Day02.solve(::part2))
    }
}
