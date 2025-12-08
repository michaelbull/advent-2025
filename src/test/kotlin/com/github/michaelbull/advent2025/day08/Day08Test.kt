package com.github.michaelbull.advent2025.day08

import com.github.michaelbull.advent2025.day08.Day08.part1
import com.github.michaelbull.advent2025.day08.Day08.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {

    @Test
    fun `example 1`() {
        val input = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """

        val playground = Day08.parse(input)
        assertEquals(40, playground.largestCircuitsProduct(connections = 10))
    }

    @Test
    fun `example 2`() {
        val input = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """

        val playground = Day08.parse(input)
        assertEquals(25272, playground.finalConnectionProduct())
    }

    @Test
    fun `answer 1`() {
        assertEquals(164475, Day08.solve(::part1))
    }

    @Test
    fun `answer 2`() {
        assertEquals(169521198, Day08.solve(::part2))
    }
}
