package com.github.michaelbull.advent2025.day10

import com.github.michaelbull.advent2025.day10.Day10.part1
import com.github.michaelbull.advent2025.day10.Day10.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    @Test
    fun `example 1`() {
        val input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """

        assertEquals(7L, Day10.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """

        assertEquals(33L, Day10.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(425L, Day10.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(15883L, Day10.solve(::part2))
    }
}
