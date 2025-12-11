package com.github.michaelbull.advent2025.day11

import com.github.michaelbull.advent2025.day11.Day11.part1
import com.github.michaelbull.advent2025.day11.Day11.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun `example 1`() {
        val input = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
        """

        assertEquals(5L, Day11.solve(::part1, input))
    }

    @Test
    fun `example 2`() {
        val input = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """

        assertEquals(2L, Day11.solve(::part2, input))
    }

    @Test
    fun `answer 1`() {
        assertEquals(472L, Day11.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(526811953334940L, Day11.solve(::part2))
    }
}
