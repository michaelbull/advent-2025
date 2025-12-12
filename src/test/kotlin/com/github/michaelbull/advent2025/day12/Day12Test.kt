package com.github.michaelbull.advent2025.day12

import com.github.michaelbull.advent2025.day12.Day12.part1
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    @Test
    fun `example 1`() {
        val input = """
            0:
            ###
            ##.
            ##.

            1:
            ###
            ##.
            .##

            2:
            .##
            ###
            ##.

            3:
            ##.
            ###
            ##.

            4:
            ###
            #..
            ###

            5:
            ###
            .#.
            ###

            4x4: 0 0 0 0 2 0
            12x5: 1 0 1 0 2 2
            12x5: 1 0 1 0 3 2
        """

        assertEquals(2, Day12.solve(::part1, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(443, Day12.solve(::part1))
    }
}
